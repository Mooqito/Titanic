package model.Order;

import database.DBconnection;

import java.security.Timestamp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GetfFeatureOrder {

    public static List<Order> getAllOrder (){
        Connection connection = DBconnection.connect();
        List<Order> list = new ArrayList<>();
        String Query = "SELECT * FROM order ORDER BY order_date DESC";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Order order = new Order(
                        resultSet.getInt("id"),
                        resultSet.getInt("customer_id"),
                        resultSet.getTimestamp("order_date").toLocalDateTime(),
                        resultSet.getDouble("total_amount"));
                list.add(order);
            }
        }catch (SQLException e){
            e.getMessage();
        }

    }
}
