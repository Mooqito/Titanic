package model.Product.brand;

import database.DBconnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class getAllBrand {

    public static List<Brand> brand (){
        List<Brand> list = new ArrayList<>();

        Connection connection = DBconnection.connect();
        Scanner scanner = new Scanner(System.in);

        String Query = "SELECT * FROM Brand";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Query);
            ResultSet resultSet = preparedStatement.executeQuery();


            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String title = resultSet.getString("title");

                Brand brand = new Brand(title);
                list.add(brand);
            }
            return list;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
