package runner;

import controller.Authnticate.AuthMenu;
import database.DBinitialazation.DbInitializerAuth;
import database.DBinitialazation.DbInitializerProduct;

public class Main {
    public static void main(String[] args) {
        DbInitializerAuth.initAuth();
        DbInitializerProduct.initProduct();
        new AuthMenu();
    }
}
