package model.Product.brand;

import database.DBconnection;
import model.Product.brand.Brand;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BrandInputToDB {

    public static boolean brandinput (Brand brand){

        Connection connection = DBconnection.connect();

        String Query = "INSERT INTO Brand (title)" + "VALUES (?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Query);

            preparedStatement.setString(1,brand.getTitle());

            preparedStatement.executeUpdate();
            return true;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
