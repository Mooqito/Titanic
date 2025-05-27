package database;

import model.Product.Provider;

import java.security.PrivilegedAction;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProviderInputToDB {

    public static boolean providerinput (Provider provider){
        Connection connection = DBconnection.connect();
        String Query = "INSERT INTO Provider (title)"+"VALUES (?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Query);
            preparedStatement.setString(1,provider.getTitle());

            preparedStatement.executeUpdate();
            return true;
        }catch (SQLException e){
            System.out.println(e.getMessage());
            return false;
        }
    }
}
