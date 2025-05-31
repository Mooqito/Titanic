package controller.Authnticate;

import java.util.Scanner;

public class Login {
    Scanner scanner = new Scanner(System.in);

    public static boolean login (String user, String pass){
        String username,password,gmail;

        username = user;

        password = pass;


        if (AuthService.login(username,password)){
            return true;
        }else {
            return false;
        }

    }
}
