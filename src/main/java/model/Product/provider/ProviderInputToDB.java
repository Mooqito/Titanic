package model.Product.provider;

import database.DBconnection;
import model.Product.provider.Provider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProviderInputToDB {

    public static boolean providerinput (String title){
        Connection connection = DBconnection.connect();
        String Query = "INSERT INTO Provider (title)"+"VALUES (?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Query);
            preparedStatement.setString(1,title);

            preparedStatement.executeUpdate();
            return true;
        }catch (SQLException e){
            System.out.println(e.getMessage());
            return false;
        }
    }
}
