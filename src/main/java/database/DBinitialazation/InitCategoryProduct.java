package database.DBinitialazation;

import database.DBconnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class InitCategoryProduct {

    public static void initCategory() {
        try {
            Connection connection = DBconnection.connect();
            Statement statement = connection.createStatement();

            String Query = """
                    CREATE TABLE IF NOT EXISTS Category (
                    id SERIAL PRIMARY KEY NOT NULL,
                    Title VARCHAR(100) NOT NULL
                    );
                    """;

            statement.executeUpdate(Query);
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
