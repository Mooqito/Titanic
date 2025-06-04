package model.Product.category;

import database.DBconnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GetAllCategory {

    public static List<String> category() {
        List<String> list = new ArrayList<>();
        Connection connection = DBconnection.connect();
        Scanner scanner = new Scanner(System.in);

        String Query="SELECT * FROM Category";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                long id = resultSet.getLong("id");
                String title = resultSet.getString("title");
                list.add(title);
            }

            return list;

        }catch (SQLException e){
            System.out.println(e.getMessage());
            return null;
        }

    }
}
