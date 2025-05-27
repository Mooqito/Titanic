package model.Product;

import database.DBconnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReadAllproduct {

    public static List<Product> Readproduct() {

        List<Product> products = new ArrayList<>();

        Connection connection = DBconnection.connect();

        String Query = "SELECT * FROM Product";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                String title = resultSet.getString("title");
                long price = resultSet.getLong("price");
                String description = resultSet.getString("description");
                long category_id = resultSet.getLong("category");
                long brand_id = resultSet.getLong("brand");
                long provider_id = resultSet.getLong("provider");

                Product product = new Product(title,price,description,category_id,brand_id,provider_id);
                products.add(product);
            }

        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return products;
    }
}
