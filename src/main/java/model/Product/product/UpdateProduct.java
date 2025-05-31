package model.Product.product;

import database.DBconnection;
import model.Product.product.Product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateProduct {

    public static boolean updateProduct(Product product) {
        Connection connection = DBconnection.connect();

        String Query = """
            UPDATE product 
            SET title = ?,
                price = ?,
                description = ?,
                category_id = ?,
                brand_id = ?,
                provider_id = ?,
                Quantity = ?
            WHERE id = ?
            """;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Query);

            preparedStatement.setString(1, product.getTitle());
            preparedStatement.setLong(2, product.getPrice());
            preparedStatement.setString(3, product.getDescription());
            preparedStatement.setLong(4, product.getCategory_id());
            preparedStatement.setLong(5, product.getBrand_id());
            preparedStatement.setLong(6, product.getProviders_id());
            preparedStatement.setLong(7,product.getQuantity());
            preparedStatement.setLong(8, product.getId());

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("خطا در بروزرسانی محصول: " + e.getMessage());
            return false;
        }
    }

    public static boolean updateProductPrice(long productId, long newPrice) {
        Connection connection = DBconnection.connect();

        String Query = "UPDATE product SET price = ? WHERE id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Query);

            preparedStatement.setLong(1, newPrice);
            preparedStatement.setLong(2, productId);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("خطا در بروزرسانی قیمت محصول: " + e.getMessage());
            return false;
        }
    }

    public static boolean updateProductQuantity(long productId, long newQuantity) {
        Connection connection = DBconnection.connect();

        String Query = "UPDATE product SET Quantity = ? WHERE id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Query);

            preparedStatement.setLong(1, newQuantity);
            preparedStatement.setLong(2, productId);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("خطا در بروزرسانی قیمت محصول: " + e.getMessage());
            return false;
        }
    }

    public static boolean updateProductCategory(long productId, long newCategoryId) {
        Connection connection = DBconnection.connect();

        String Query = "UPDATE product SET category_id = ? WHERE id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Query);

            preparedStatement.setLong(1, newCategoryId);
            preparedStatement.setLong(2, productId);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("خطا در بروزرسانی دسته‌بندی محصول: " + e.getMessage());
            return false;
        }
    }

    public static boolean updateProductBrand(long productId, long newBrandId) {
        Connection connection = DBconnection.connect();

        String Query = "UPDATE product SET brand_id = ? WHERE id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Query);

            preparedStatement.setLong(1, newBrandId);
            preparedStatement.setLong(2, productId);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("خطا در بروزرسانی برند محصول: " + e.getMessage());
            return false;
        }
    }

    public static boolean updateProductProvider(long productId, long newProviderId) {
        Connection connection = DBconnection.connect();

        String Query = "UPDATE product SET provider_id = ? WHERE id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Query);

            preparedStatement.setLong(1, newProviderId);
            preparedStatement.setLong(2, productId);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("خطا در بروزرسانی تامین‌کننده محصول: " + e.getMessage());
            return false;
        }
    }
}