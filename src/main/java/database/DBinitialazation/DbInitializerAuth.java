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

            String createAuthnticateTable = """
                    CREATE TABLE IF NOT EXISTS authnticate (
                        id SERIAL PRIMARY KEY,
                        username VARCHAR(100) NOT NULL UNIQUE,
                        password VARCHAR(100) NOT NULL
                        );
                    """;
            statement.executeUpdate(createAuthnticateTable);
            System.out.println("CREATE OK");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
