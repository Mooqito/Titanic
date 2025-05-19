package Data_Base;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBconnection {
    private static final String url ="jdbc:postgresql://localhost:5432/store_db"; //address database in postgresql
    private static final String user ="postgres"; //username postgresql
    private static final String password ="postgres";//password postgresql

    private static Connection connection;

    public DBconnection(){}

    public static Connection connect () {
        try {

            connection = DriverManager.getConnection(url,user,password); //connection to database

        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return connection;
    }
}
