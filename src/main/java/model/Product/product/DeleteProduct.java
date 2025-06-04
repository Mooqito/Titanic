package model.Product.product;

import database.DBconnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteProduct {

    public static boolean deleteProduct(String title) {
        Connection connection = DBconnection.connect();

        String Query = "DELETE FROM product WHERE title = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Query);
            preparedStatement.setString(1, title);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
