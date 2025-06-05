package model.Product.category;

import database.DBconnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DeleteCategory {

    public static boolean category (String title){
        Connection connection = DBconnection.connect();
        String Query = "DELETE FROM Category WHERE title = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Query);
            preparedStatement.setString(1,title);
            preparedStatement.executeQuery();
            return true;

        }catch (SQLException e){
            e.getMessage();
            return false;
        }
    }
}
