package model.Product.product;

import database.DBconnection;
import model.Product.product.Product;

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

        String Query = """
            SELECT 
                p.id, p.title, p.price, p.description,
                p.category_id, p.brand_id, p.provider_id,
                c.title as category_title,
                b.title as brand_title,
                pr.title as provider_title
            FROM product p
            LEFT JOIN category c ON p.category_id = c.id
            LEFT JOIN brand b ON p.brand_id = b.id
            LEFT JOIN provider pr ON p.provider_id = pr.id
            """;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String title = resultSet.getString("title");
                long price = resultSet.getLong("price");
                String description = resultSet.getString("description");
                long category_id = resultSet.getLong("category_id");
                long brand_id = resultSet.getLong("brand_id");
                long provider_id = resultSet.getLong("provider_id");

                Product product = new Product(id,title, price, description, category_id, brand_id, provider_id);
                
                // Set the titles
                product.setCategoryTitle(resultSet.getString("category_title"));
                product.setBrandTitle(resultSet.getString("brand_title"));
                product.setProviderTitle(resultSet.getString("provider_title"));

                products.add(product);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return products;
    }
}
