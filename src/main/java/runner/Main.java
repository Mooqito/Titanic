package runner;

import controller.Authnticate.AuthMenu;
import database.DbInitializerAuth;
import database.DbInitializerProduct;

public class Main {
    public static void main(String[] args) {
        DbInitializerAuth.initAuth();
        DbInitializerProduct.initProduct();
        new AuthMenu();
    }
}
