package controller;


import model.AuthService;

import java.util.Scanner;


// class wellcom to app and menu for Login,Sing up,Reset password user
public class AuthMenu {

    Scanner input  = new Scanner(System.in);


    //functio Sing up
    public void Sing_up() {

        System.out.println("user name : ");
        String username = input.next(); // getting username

        System.out.println("password : "); // getting password
        String password = input.next();

        //Sends the password and username to the AuthService function to build an arithmetic
        if (AuthService.register(username,password)){
            System.out.println("Registered successfully."); // If no other username with this name was account
//            Shop_menu
        }else {
            System.out.println("Registration failed."); // If no other username with this name was't account
        }
    }

    //function login
    public void Login() {

        System.out.println("user name : ");
        String username = input.next();// getting username

        System.out.println("password : ");
        String password = input.next();// getting password

        // Sends the username and password to the AuthService function to lonig user in app
        if (AuthService.login(username,password)){
            System.out.println("Login successful!"); //if correct username and password user login
//            Shop_menu
        }else {
            System.out.println("Invalid credentials."); //if wrong username and password Invalid credentials
        }
    }

    // function reset password
    public void Forget_password() {

        System.out.println("user name : ");
        String username = input.next();// getting username

        System.out.println("password : ");
        String password = input.next();// getting password

        // Sends the username and password to the AuthService function reset password
        if (AuthService.resetPassword(username,password)){
            System.out.println("Password reset successful."); //if correct username ->reset password
//            new LoginMenu();
        }else {
            System.out.println("User not found. Try again.");//if wrong username ->don't reset
        }
    }
}
