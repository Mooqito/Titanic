package model.Product.product;

import database.DBconnection;
import model.Product.product.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductInputToDB {


    public static boolean productExist (String name, long price, String category, String provider, String brand){
        Connection connection = DBconnection.connect();

        String Query = "SELECT 1 FROM product p " +
                "JOIN category c ON p.category_id = c.id " +
                "JOIN provider pr ON p.provider_id = pr.id " +
                "JOIN brand b ON p.brand_id = b.id " +
                "WHERE p.name = ? AND p.price = ? AND c.name = ? AND pr.name = ? AND b.name = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Query);

            preparedStatement.setString(1,name);
            preparedStatement.setLong(2,price);
            preparedStatement.setString(3,category);
            preparedStatement.setString(4,provider);
            preparedStatement.setString(5,brand);

            ResultSet resultSet = preparedStatement.executeQuery();

            return resultSet.next();

        }catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public static boolean updateProductQuantity (String name, long price, String category, String provider, String brand, int additionalQuantity){
        Connection connection = DBconnection.connect();

        String Query = "UPDATE product SET Quantity = Quantity + ? " +
                "WHERE id IN (SELECT p.id FROM product p " +
                "JOIN category c ON p.category_id = c.id " +
                "OIN provider pr ON p.provider_id = pr.id " +
                "JOIN brand b ON p.brand_id = b.id " +
                "WHERE p.name = ? AND p.price = ? AND c.name = ? AND pr.name = ? AND b.name = ?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Query);

            preparedStatement.setLong(1,additionalQuantity);
            preparedStatement.setString(2,name);
            preparedStatement.setLong(3,price);
            preparedStatement.setString(4,category);
            preparedStatement.setString(5,provider);
            preparedStatement.setString(6,brand);

            return preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }

    }

    public static boolean productInput(Product product) {

        Connection connection = DBconnection.connect();

        String Query = "INSERT INTO product (title,price,description,category_id,brand_id,provider_id,Quantity)" + "VALUES (?,?,?,?,?,?,?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Query);

            preparedStatement.setString(1,product.getTitle());
            preparedStatement.setLong(2,product.getPrice());
            preparedStatement.setString(3,product.getDescription());
            preparedStatement.setLong(4,product.getCategory_id());
            preparedStatement.setLong(5,product.getBrand_id());
            preparedStatement.setLong(6,product.getProviders_id());
            preparedStatement.setLong(7,product.getQuantity());

            preparedStatement.executeUpdate();
            return true;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
