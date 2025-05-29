package controller.Authnticate;


import model.Authneticate.AuthService;
import view.Shop;
import view.Shop;

import java.util.Scanner;


// class wellcom to app and menu for Login,Sing up,Reset password user
public class AuthMenu {

    Shop s = new Shop();

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
                    SingUp sing=new SingUp();
                    sing.sing_up();
                    break;
                case 2:
                    Login login = new Login();
                    login.login();
                    break;
                case 3:
                    Forget_password(); //function reset password
                    break;

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
