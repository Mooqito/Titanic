package view;


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
                    break;
                case 2:
                    break;
                case 3:
                    break;

            }
        }
}
