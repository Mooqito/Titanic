package database.DBinitialazation;

import database.DBconnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DbInitializerProduct {

    public static void initProduct() {
        try {
            Connection connection = DBconnection.connect();
            Statement statement = connection.createStatement();

            String createTableProduct = """
                    CREATE TABLE IF NOT EXISTS Product (
                        id serial PRIMARY KEY NOT NULL,
                        title VARCHAR(100) NOT NULL UNIQUE,
                        price BIGINT NOT NULL,
                        description TEXT,
                        category VARCHAR(100) NOT NULL,
                        brand VARCHAR(100) NOT NULL,
                        privider TEXT NOT NULL
                        );
                    """;
            statement.executeUpdate(createTableProduct);
            System.out.println("create table product");
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
