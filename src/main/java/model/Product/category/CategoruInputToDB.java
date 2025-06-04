package model.Product.category;

import database.DBconnection;
import model.Product.category.Category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CategoruInputToDB {

    public static boolean categoryInput(String title) {

        Connection connection = DBconnection.connect();

        String Query = "INSERT INTO Category (title)" + "VALUES (?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Query);

            preparedStatement.setString(1,title);

            preparedStatement.executeUpdate();
            return true;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
