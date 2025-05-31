package model.Product.product;

import database.DBconnection;
import model.Product.product.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductInputToDB {


    public static boolean productExist (String tile, long price, String category, String provider, String brand){
        Connection connection = DBconnection.connect();

        String Query = "SELECT 1 FROM product p " +
                "JOIN category c ON p.category_id = c.id " +
                "JOIN provider pr ON p.provider_id = pr.id " +
                "JOIN brand b ON p.brand_id = b.id " +
                "WHERE p.title = ? AND p.price = ? AND c.title = ? AND pr.title = ? AND b.title = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Query);

            preparedStatement.setString(1,tile);
            preparedStatement.setLong(2,price);
            preparedStatement.setString(3,category);
            preparedStatement.setString(4,provider);
            preparedStatement.setString(5,brand);

            ResultSet resultSet = preparedStatement.executeQuery();

            return resultSet.next();

        }catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public static boolean updateProductQuantity (String title, long price, String category, String provider, String brand,long Quantity){
        Connection connection = DBconnection.connect();

        String Query = "UPDATE product SET Quantity = Quantity + ? " +
                "WHERE id IN (SELECT p.id FROM product p " +
                "JOIN category c ON p.category_id = c.id " +
                "JOIN provider pr ON p.provider_id = pr.id " +
                "JOIN brand b ON p.brand_id = b.id " +
                "WHERE p.title = ? AND p.price = ? AND c.title = ? AND pr.title = ? AND b.title = ?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Query);

            preparedStatement.setLong(1,Quantity);
            preparedStatement.setString(2,title);
            preparedStatement.setLong(3,price);
            preparedStatement.setString(4,category);
            preparedStatement.setString(5,provider);
            preparedStatement.setString(6,brand);

            return preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }

    }

    public static boolean productInput(String title, long price,String description, String category, String provider, String brand,long Quantity) {



        Connection connection = DBconnection.connect();

        String Query = "INSERT INTO product (title,price,description,category_id,brand_id,provider_id,Quantity)" + "VALUES (?,?,?,?,?,?,?)";

        try {
            int category_Id = getOrCreateId(connection, "category", category);
            int provider_Id = getOrCreateId(connection, "provider", provider);
            int brand_Id = getOrCreateId(connection, "brand",brand);

            PreparedStatement preparedStatement = connection.prepareStatement(Query);

            preparedStatement.setString(1,title);
            preparedStatement.setLong(2,price);
            preparedStatement.setString(3,description);
            preparedStatement.setLong(4,category_Id);
            preparedStatement.setLong(5,provider_Id);
            preparedStatement.setLong(6,brand_Id);
            preparedStatement.setLong(7,Quantity);

            preparedStatement.executeUpdate();
            return true;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private static int getOrCreateId(Connection conn, String table, String name) throws SQLException {
        PreparedStatement selectStmt = conn.prepareStatement(
                "SELECT id FROM " + table + " WHERE name = ?"
        );
        selectStmt.setString(1, name);
        ResultSet rs = selectStmt.executeQuery();

        if (rs.next()) {
            return rs.getInt("id");
        }throw new SQLException("Failed to create new " + table + " entry");
    }
}
