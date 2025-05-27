package controller.product;

import database.DBconnection;
import model.Product.Brand;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class SelectBrand {

    public static long select (){

        Connection connection = DBconnection.connect();
        Scanner scanner = new Scanner(System.in);

        String Query = "SELECT * FROM Brand";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Query);
            ResultSet resultSet = preparedStatement.executeQuery();

            boolean isEmpty = true;

            while (resultSet.next()) {
                isEmpty=false;
                long id = resultSet.getLong("id");
                String title = resultSet.getString("title");
                System.out.println(id + " - " + title);
            }

            if (isEmpty) {
                System.out.println("⚠ هیچ برندی وجود ندارد. ابتدا یک برند تعریف کنید.");
                return -1;
            }

            System.out.print("Enter Category ID from the above list: ");
            return Long.parseLong(scanner.nextLine());

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return -1;
        }
    }
}
