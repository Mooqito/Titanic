package model.Product.brand;

import database.DBconnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GetAllBrand {

    public static List<String> brand (){
        List<String> list = new ArrayList<>();

        Connection connection = DBconnection.connect();
        Scanner scanner = new Scanner(System.in);

        String Query = "SELECT * FROM Brand";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Query);
            ResultSet resultSet = preparedStatement.executeQuery();


            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String title = resultSet.getString("title");

                list.add(title);
            }
            return list;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    public static List<Brand> getAllBrand() {
        List<Brand> brands = new ArrayList<>();
        Connection connection = DBconnection.connect();

        String Query = "SELECT * FROM Brand";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String title = resultSet.getString("title");
                brands.add(new Brand(id, title));
            }
            return brands;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return new ArrayList<>();
        }
    }
}
