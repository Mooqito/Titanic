package model.Authneticate;


import database.DBconnection;

import java.sql.*;
import java.sql.Connection;

// Authntication function
public class AuthService {

    //    The function of the register and the password
    public static boolean register(String username , String password) {

        Connection connection = DBconnection.connect(); //When the database is connected, the address and database returns to the desert in it

        String hashedpassword = HashUtil.hashpassword(password); // send tse password to the Hash function for hashed password
        String Query = "INSERT INTO authnticate (username,password)"//query insert username and password
                +"VALUES (?,?)";

        try{

            PreparedStatement preparedStatement = connection.prepareStatement(Query); //possiblity of entering information in the table

            preparedStatement.setString(1,username); //enter username in tha table
            preparedStatement.setString(2,hashedpassword); //enter password in the table
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
    public static boolean login(String username ,String password) {

        Connection connection = DBconnection.connect();//When the database is connected, the address and database returns to the desert in it

        String hashedpassword = HashUtil.hashpassword(password);// send tse password to the Hash function for hashed password
        String Query = "SELECT FROM authnticate WHERE username = ? AND password = ?"; //query login

        try {

            PreparedStatement preparedStatement  = connection.prepareStatement(Query);//possiblity of entering information in the table

            preparedStatement.setString(1,username);//enter username in tha table
            preparedStatement.setString(2,hashedpassword);//enter password in the table

            ResultSet resultSet = preparedStatement.executeQuery(); //Returns the number as many rows that change
            return resultSet.next();

        } catch (SQLException e) {
            System.out.println("Error : " + e.getMessage());
            return false;
        }
    }

    public static boolean resetPassword(String usernam,String password) {

        Connection connection = DBconnection.connect();//When the database is connected, the address and database returns to the desert in it

        String hashedpassword = HashUtil.hashpassword(password);// send tse password to the Hash function for hashed password

        String Query = "UPDATE authnticate SET password = ? WHERE username = ?";//query updata password
        try {

            PreparedStatement preparedStatement = connection.prepareStatement(Query);//possiblity of entering information in the table


            preparedStatement.setString(1,hashedpassword);//enter username in tha table
            preparedStatement.setString(2,usernam);//enter password in the table

            int affectedRows = preparedStatement.executeUpdate();//Returns the number as many rows that change

            return affectedRows>0;

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }
}

