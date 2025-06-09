package database.DBinitialazation;

import database.DBconnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InitOrder {

    public static void order (){
        Connection connection = DBconnection.connect();
        String Query = "CREATE TABLE IF NOT EXISTS order " +
                "(id serial PRIMARY KEY," +
                "customer_id INTEGER REFERENCES customer(id)," +
                "order_date TIMESTAMP," +
                "total_amount DECIMAL(10,2)" +
                ");";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Query);
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.getMessage();
        }
    }
}
