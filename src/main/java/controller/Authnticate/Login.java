package controller.Authnticate;

import model.Authneticate.AuthService;
import model.Authneticate.User;
import view.Shop;

import java.util.Scanner;

public class Login {
    Scanner scanner = new Scanner(System.in);

    public void login (){
        String username,password,gmail;

        System.out.println("نام کاربری را وراد کنید");
        username = scanner.next();

        System.out.println("پسورد را وراد کنید");
        password = scanner.next();

        System.out.println("جیمیل را وارد کنید");
        gmail=scanner.next();

        User user = new User(username,password,gmail);
        if (AuthService.login(user)){
            System.out.println("Registered successfully."); // If no other username with this name was account
            Shop s = new Shop();
            s.shop();
        }else {
            System.out.println("Registration failed."); // If no other username with this name was't account
        }

    }
}
