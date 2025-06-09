package model.Order;

import database.DBconnection;
import org.postgresql.jdbc.PgConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class OrderItemInputToDB {

    public static void orderItem(int orderId, List<OrderItem> orderItems) {
        Connection connection = DBconnection.connect();
        String Query = "INSERT INTO order_items (order_id, product_id, quantity, unit_price) VALUES (?, ?, ?, ?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Query);
            for (OrderItem item : orderItems) {
                preparedStatement.setInt(1, orderId);
                preparedStatement.setInt(2, item.getProductId());
                preparedStatement.setInt(3, item.getQuantity());
                preparedStatement.setDouble(4, item.getUnitPrice());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.getMessage();
        }
    }
}