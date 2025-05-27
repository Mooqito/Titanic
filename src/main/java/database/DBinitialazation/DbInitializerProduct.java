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
                        Id serial PRIMARY KEY NOT NULL,
                        Title VARCHAR(100) NOT NULL UNIQUE,
                        Price BIGINT NOT NULL,
                        Description TEXT,
                        category VARCHAR(100) NOT NULL,
                        brand VARCHAR(100) NOT NULL,
                        provider TEXT NOT NULL
                        );
                    """;
            statement.executeUpdate(createTableProduct);
            System.out.println("create table product");
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
