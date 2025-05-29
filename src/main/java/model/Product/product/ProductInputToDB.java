package model.Product.product;

import database.DBconnection;
import model.Product.product.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProductInputToDB {

    public static boolean productInput(Product product) {

        Connection connection = DBconnection.connect();

        String Query = "INSERT INTO product (title,price,description,category_id,brand_id,provider_id)" + "VALUES (?,?,?,?,?,?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Query);

            preparedStatement.setString(1,product.getTitle());
            preparedStatement.setLong(2,product.getPrice());
            preparedStatement.setString(3,product.getDescription());
            preparedStatement.setLong(4,product.getCategory_id());
            preparedStatement.setLong(5,product.getBrand_id());
            preparedStatement.setLong(6,product.getProviders_id());

            preparedStatement.executeUpdate();
            return true;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
