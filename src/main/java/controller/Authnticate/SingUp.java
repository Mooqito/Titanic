package controller.Authnticate;

import model.Authneticate.AuthService;
import model.Authneticate.User;
import view.Shop;

import java.util.Scanner;
import java.util.regex.Pattern;

public class SingUp {

    Scanner scanner = new Scanner(System.in);

    public void sing_up (){
        String username,password,confirmPassword,gmail;

        while (true){
            System.out.println("نام کاربری را وراد کنید (انگلبسی باشد)");
            username = scanner.next();
            if (!Pattern.matches("^[a-zA-Z0-9_]{4,}$",username)){
                System.out.println("user error");
            }else break;
        }

        while (true){
            System.out.println("پسورد را وراد کنید");
            password = scanner.next();

            if (!Pattern.matches("^[a-zA-Z0-9!@#$%^&*()_+]{8,}$",password)){
                System.out.println("password error");
            }else break;
        }

        while (true){
            System.out.println("confipass");
            confirmPassword=scanner.next();

            if (!password.equals(confirmPassword)){
                System.out.println("password error");
            }else break;
        }

        while (true){
            System.out.println("جیمیل را وارد کنید");
            gmail=scanner.next();
            if (!Pattern.matches("^[a-zA-Z0-9!@#$%^&*()_+//]+@gmail\\.com$",gmail)){
                System.out.println("gmail error");
            }else break;
        }

        User user = new User(username,password,gmail);
        if (AuthService.register(user)){
            System.out.println("Registered successfully."); // If no other username with this name was account
            Shop s = new Shop();
            s.shop();
        }else {
            System.out.println("Registration failed."); // If no other username with this name was't account
        }
    }
}
