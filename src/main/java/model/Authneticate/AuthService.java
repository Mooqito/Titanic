package model.Authneticate;


import database.DBconnection;

import java.sql.*;
import java.sql.Connection;

// Authntication function
public class AuthService {

    //    The function of the register and the password
    public static boolean register(User user) {

        Connection connection = DBconnection.connect(); //When the database is connected, the address and database returns to the desert in it

        String hashedpassword = HashUtil.hashpassword(user.getPassword()); // send tse password to the Hash function for hashed password
        String Query = "INSERT INTO Authentication (username,password,gmail)"//query insert username and password
                +"VALUES (?,?,?)";

        try{

            PreparedStatement preparedStatement = connection.prepareStatement(Query); //possiblity of entering information in the table

            preparedStatement.setString(1,user.getUser_name()); //enter username in tha table
            preparedStatement.setString(2,hashedpassword); //enter password in the table
            preparedStatement.setString(3,user.getGmail());
            preparedStatement.executeUpdate(); //update table
            return true;
        } catch (SQLException e) {
            if (e.getMessage().contains("duplicate key value")) {
                System.out.println("Username already exists.");
            } else {
                System.out.println("Error: " + e.getMessage());
            }
        }
        return false;
    }

    //    The function of the login
    public static boolean login(String user,String pass) {

        Connection connection = DBconnection.connect();//When the database is connected, the address and database returns to the desert in it

        String hashedpassword = HashUtil.hashpassword(pass);// send tse password to the Hash function for hashed password
        String Query = "SELECT FROM Authentication WHERE username = ? AND password = ?"; //query login

        try {

            PreparedStatement preparedStatement  = connection.prepareStatement(Query);//possiblity of entering information in the table

            preparedStatement.setString(1,user);//enter username in tha table
            preparedStatement.setString(2,hashedpassword);//enter password in the table

            ResultSet resultSet = preparedStatement.executeQuery(); //Returns the number as many rows that change
            return resultSet.next();

        } catch (SQLException e) {
            System.out.println("Error : " + e.getMessage());
            return false;
        }
    }

    public static boolean resetPassword(User user) {

        Connection connection = DBconnection.connect();//When the database is connected, the address and database returns to the desert in it

        String hashedpassword = HashUtil.hashpassword(user.getPassword());// send tse password to the Hash function for hashed password

        String Query = "UPDATE Authentication SET password = ? WHERE username = ? AND gmail = ?";//query updata password
        try {

            PreparedStatement preparedStatement = connection.prepareStatement(Query);//possiblity of entering information in the table


            preparedStatement.setString(1,hashedpassword);//enter username in tha table
            preparedStatement.setString(2,user.getUser_name());//enter password in the table
            preparedStatement.setString(3,user.getGmail());

            int affectedRows = preparedStatement.executeUpdate();//Returns the number as many rows that change

            return affectedRows>0;

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }

    public static String FindUser (String username, String gmail){

        String query = "SELECT * FROM users WHERE username = ? AND email = ?";
        try (Connection connection = DBconnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, gmail);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String foundUsername = resultSet.getString("username");
                return username;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}

