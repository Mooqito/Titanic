package model.Order;

import database.DBconnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderInputToDB {

    public static int order (Order order){
        Connection connection = DBconnection.connect();
        String Query = "INSERT INTO order (customer_id,order_date,total_amount)" +
                "VALUES (?,?,?) RETURNING id";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Query);
            preparedStatement.setInt(1,order.getCustomerId());
            preparedStatement.setTimestamp(2,order.getOrderDate());
            preparedStatement.setDouble(3,order.getTotalAmount());

            ResultSet resultSet= preparedStatement.executeQuery();

            if (resultSet.next()){
                return order.getId();
            }

        }catch (SQLException e){
            e.getMessage();
        }
        return 0;
    }
}
