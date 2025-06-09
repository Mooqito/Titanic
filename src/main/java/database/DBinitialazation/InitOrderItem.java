package database.DBinitialazation;

import database.DBconnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InitOrderItem {

    public static void order_item (){
        Connection connection = DBconnection.connect();
        String Query = "CRATE TABLE IF NOT EXISTS order_item" +
                "( id serial PRIMARY KEY," +
                "order_id INTEGER REFERENCES order(id)," +
                "product_id INTEGER REFERENCES Product(id)," +
                "quantity INTEGER," +
                "unit_price DECIMAL(10,2)" +
                ");";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Query);
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.getMessage();
        }
    }
}
