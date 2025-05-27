package database.DBinitialazation;

import database.DBconnection;
import org.postgresql.jdbc.PgConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class InitBrandProduct {

    public static void initbrand() {

        try {
            Connection connection = DBconnection.connect();
            Statement statement = connection.createStatement();

            String Query = """
                    CREATE TABLE IF NOT EXISTS Brand (
                    Id serial PRIMARY KEY NOT NULL,
                    Title VARCHAR(100) NOT NULL
                    );
                    """;
            statement.executeUpdate(Query);
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
