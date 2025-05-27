package runner;

import controller.Authnticate.AuthMenu;
import database.DBinitialazation.*;

public class Main {
    public static void main(String[] args) {
        InitCategoryProduct.initCategory();
        InitBrandProduct.initbrand();
        InitProviderProduct.inintprovider();
        DbInitializerAuth.initAuth();
        DbInitializerProduct.initProduct();
        new AuthMenu();
    }
}
