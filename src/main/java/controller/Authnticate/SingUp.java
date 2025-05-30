package controller.Authnticate;

import model.Authneticate.AuthService;
import model.Authneticate.User;
import view.Shop;

import java.util.Scanner;
import java.util.regex.Pattern;

public class SingUp {

    Scanner scanner = new Scanner(System.in);

    public static boolean sing_up (String user, String pass, String cinfipass, String email){
        String username,password,confirmPassword,emailAdress;

            username = user;
            if (!Pattern.matches("^[a-zA-Z0-9_]{4,}$",username)){
                return false;
            }

            password = pass;
            if (!Pattern.matches("^[a-zA-Z0-9!@#$%^&*()_+]{8,}$",password)){
                return false;
            }

            confirmPassword=cinfipass
            if (!password.equals(confirmPassword)){
                return false;
            }

            emailAdress=email;
            if (!Pattern.matches("^[a-zA-Z0-9!@#$%^&*()_+//]+@gmail\\.com$",emailAdress)){
                return false;
            }

        User u = new User(username,password,emailAdress);
        if (AuthService.register(u)){
            return true;
        }else {
            return false;
        }
    }
}
