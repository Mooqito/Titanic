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
                long id = resultSet.getLong("id");
                String title = resultSet.getString("title");
                long price = resultSet.getLong("price");
                String description = resultSet.getString("description");
                String category = resultSet.getString("category");
                String brand = resultSet.getString("brand");
                String provider = resultSet.getString("privider");

                Product product = new Product(id,title,price,description,
                        new Category(category),
                        new Provider(provider),
                        new model.Product.Brand(brand));

                products.add(product);
            }

        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return products;
    }
}
