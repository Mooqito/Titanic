package model.Product.brand;

import database.DBconnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteBrand {

    public static boolean deleteBrand(String title) {
        Connection connection = DBconnection.connect();

        String Query = "DELETE FROM Brand WHERE title = ?";

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