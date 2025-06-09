package model.Product.product;

import database.DBconnection;
import java.sql.*;

public class ProductInputToDB {

//    // بررسی فقط عنوان برای جلوگیری از خطای تکراری
//    public static boolean isTitleDuplicate(String title) {
//        Connection connection = DBconnection.connect();
//        String checkQuery = "SELECT 1 FROM product WHERE title = ?";
//        try (PreparedStatement stmt = connection.prepareStatement(checkQuery)) {
//            stmt.setString(1, title);
//            ResultSet rs = stmt.executeQuery();
//            return rs.next();
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return true; // اگر مشکلی پیش آمد، بهتره اجازه افزودن ندیم
//        }
//    }

    // بررسی وجود محصول دقیق (برای افزایش تعداد)
    public static boolean productExist(String title, long price, String category, String provider, String brand) {
        Connection connection = DBconnection.connect();
        String query = "SELECT 1 FROM product p " +
                "JOIN category c ON p.category_id = c.id " +
                "JOIN provider pr ON p.provider_id = pr.id " +
                "JOIN brand b ON p.brand_id = b.id " +
                "WHERE p.title = ? AND p.price = ? AND c.title = ? AND pr.title = ? AND b.title = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, title);
            stmt.setLong(2, price);
            stmt.setString(3, category);
            stmt.setString(4, provider);
            stmt.setString(5, brand);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // افزایش تعداد محصول
    public static boolean updateProductQuantity(String title, long price, String category, String provider, String brand, long quantity) {
        Connection connection = DBconnection.connect();
        String query = "UPDATE product SET Quantity = Quantity + ? " +
                "WHERE id IN (SELECT p.id FROM product p " +
                "JOIN category c ON p.category_id = c.id " +
                "JOIN provider pr ON p.provider_id = pr.id " +
                "JOIN brand b ON p.brand_id = b.id " +
                "WHERE p.title = ? AND p.price = ? AND c.title = ? AND pr.title = ? AND b.title = ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, quantity);
            stmt.setString(2, title);
            stmt.setLong(3, price);
            stmt.setString(4, category);
            stmt.setString(5, provider);
            stmt.setString(6, brand);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // افزودن محصول جدید
    public static boolean productInput(String title, long price, String description, String category, String provider, String brand, long quantity) {
//        if (isTitleDuplicate(title)) {
//            System.err.println("عنوان محصول تکراری است");
//            return false;
//        }

        Connection connection = DBconnection.connect();
        String query = "INSERT INTO product (title, price, description, category_id, brand_id, provider_id, Quantity) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            int categoryId = getOrCreateId(connection, "category", category);
            int providerId = getOrCreateId(connection, "provider", provider);
            int brandId = getOrCreateId(connection, "brand", brand);

            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, title);
            stmt.setLong(2, price);
            stmt.setString(3, description);
            stmt.setInt(4, categoryId);
            stmt.setInt(5, brandId);
            stmt.setInt(6, providerId);
            stmt.setLong(7, quantity);

            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static int getOrCreateId(Connection conn, String table, String name) throws SQLException {
        PreparedStatement selectStmt = conn.prepareStatement("SELECT id FROM " + table + " WHERE title = ?");
        selectStmt.setString(1, name);
        ResultSet rs = selectStmt.executeQuery();
        if (rs.next()) return rs.getInt("id");

        PreparedStatement insertStmt = conn.prepareStatement("INSERT INTO " + table + " (title) VALUES (?) RETURNING id");
        insertStmt.setString(1, name);
        ResultSet insertRs = insertStmt.executeQuery();
        if (insertRs.next()) return insertRs.getInt("id");

        throw new SQLException("Failed to insert or fetch ID for " + name);
    }
}
