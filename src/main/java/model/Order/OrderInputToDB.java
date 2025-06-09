package model.Order;

import database.DBconnection;

import java.sql.*;

public class OrderInputToDB {

    public static int order (Order order){
        Connection connection = DBconnection.connect();
        String Query = "INSERT INTO order (customer_id,order_date,total_amount)" +
                "VALUES (?,?,?) RETURNING id";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Query);
            preparedStatement.setInt(1,order.getCustomerId());
            preparedStatement.setTimestamp(2, Timestamp.valueOf(order.getOrderDate()));
            preparedStatement.setDouble(3,order.getTotalAmount());

            ResultSet resultSet= preparedStatement.executeQuery();

            if (resultSet.next()){
                return resultSet.getInt("id");
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }
}
