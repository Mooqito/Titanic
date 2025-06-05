package model.Product.brand;

import database.DBconnection;
import model.Product.brand.Brand;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BrandInputToDB {

    public static boolean brandInput(Brand brand) {
        return brandinput(brand.getTitle());
    }

    public static boolean brandinput (String title){

        Connection connection = DBconnection.connect();

        String Query = "INSERT INTO Brand (title)" + "VALUES (?)";

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
