package model.Order;

import database.DBconnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerInputToDB {

    public static int customer (Customer customer){
        Connection connection = DBconnection.connect();
        String Query = "INSERT INTO customer (first_name,last_name,phone_number)" +
                "VALUES (?,?,?) RETURNING id";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Query);
            preparedStatement.setString(1,customer.getFirstName());
            preparedStatement.setString(2,customer.getLastName());
            preparedStatement.setString(3,customer.getPhoneNumber());

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                return customer.getId();
            }

        }catch (SQLException e){
            e.getMessage();
        }
        return 0;
    }
}
