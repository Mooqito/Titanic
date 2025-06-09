package database.DBinitialazation;

import database.DBconnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InitCustomer {

    public static void customer (){
        Connection connection = DBconnection.connect();
        String Query = "CREATE TABLE IF NOT EXISTS " +
                "( id serial PRIMARY KEY," +
                "first_name VARCHAR(100)," +
                "last_name VARCHAR(100)," +
                "phone_number VARCHAR(20)" +
                ");";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Query);
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.getMessage();
        }
    }
}
