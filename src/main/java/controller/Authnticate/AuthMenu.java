package controller.Authnticate;


import model.Authneticate.AuthService;
import view.Dashboard;

import java.util.Scanner;


// class wellcom to app and menu for Login,Sing up,Reset password user
public class AuthMenu {

    Scanner input  = new Scanner(System.in);

    public AuthMenu() {

            System.out.println("* =====  Wellcom to Electric Shop  ===== *");
            System.out.println(" 1) Sing up");
            System.out.println(" 2) Login");
            System.out.println(" 3) Forget password");

            System.out.println("choic an optin(1-3");
            int choose = input.nextInt();

            switch (choose) {
                case 1:
                    Sing_up(); //functio Sing up
                    break;
                case 2:
                    Login(); //function login
                    break;
                case 3:
                    Forget_password(); //function reset password
                    break;

            }
        }

    //functio Sing up
    public void Sing_up() {

        System.out.println("user name : ");
        String username = input.next(); // getting username

        System.out.println("password : "); // getting password
        String password = input.next();

        //Sends the password and username to the AuthService function to build an arithmetic
        if (AuthService.register(username,password)){
            System.out.println("Registered successfully."); // If no other username with this name was account
            new Dashboard();
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
            new Dashboard();
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
            new AuthMenu();
        }else {
            System.out.println("User not found. Try again.");//if wrong username ->don't reset
        }
    }
}
