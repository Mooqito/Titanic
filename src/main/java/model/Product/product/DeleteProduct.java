package model.Product.product;

import database.DBconnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteProduct {

    public static boolean deleteProductById(long productId) {
        Connection connection = DBconnection.connect();

        String deleteQuery = "DELETE FROM Product WHERE id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);
            preparedStatement.setLong(1, productId);

            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println("Error deleting product: " + e.getMessage());
            return false;
        }
    }
}
