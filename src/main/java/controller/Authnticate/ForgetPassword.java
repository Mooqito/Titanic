package controller.Authnticate;

import model.Authneticate.AuthService;

import java.util.regex.Pattern;

public class ForgetPassword {

    public static boolean rest (String user,String pass,String confipass){

        String username,password,confirmPassword;

        password = pass;
        if (!Pattern.matches("^[a-zA-Z0-9!@#$%^&*()_+]{8,}$",password)){
            return false;
        }

        confirmPassword=confipass;
        if (!password.equals(confirmPassword)){
            return false;
        }

        username = user;

        if (AuthService.resetPassword(username,password)){
            return true;
        }else {
            return false;
        }
    }
}
