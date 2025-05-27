package database;

import controller.product.ProductInput;
import model.Product.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProductInputToDB {

    public static boolean productInput(Product product) {

        Connection connection = DBconnection.connect();

        String Query = "INSERT INTO product (id,title,price,description,category,brand,privider)" + "VALUES (?,?,?,?,?,?,?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Query);

            preparedStatement.setLong(1,product.getId());
            preparedStatement.setString(2,product.getTitle());
            preparedStatement.setLong(3,product.getPrice());
            preparedStatement.setString(4,product.getDescription());
            preparedStatement.setString(5,product.getCategory_id());
            preparedStatement.setString(6,product.getBrand_id());
            preparedStatement.setString(7,product.getProviders_id());

            preparedStatement.executeUpdate();
            return true;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
