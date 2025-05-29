package database.DBinitialazation;

import database.DBconnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DbInitializerAuth {
    public static void initAuth() {
        try {
            Connection connection = DBconnection.connect();
            Statement statement = connection.createStatement();

            String Query = """
                    CREATE TABLE IF NOT EXISTS Authentication (
                        id SERIAL PRIMARY KEY,
                        username VARCHAR(100) NOT NULL UNIQUE,
                        password VARCHAR(100) NOT NULL,
                        gmail VARCHAR(100) NOT NULL
                        );
                    """;
            statement.executeUpdate(Query);
            System.out.println("CREATE OK");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
