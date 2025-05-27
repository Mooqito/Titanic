package controller.product;

import database.BrandInputToDB;
import database.CategoruInputToDB;
import model.Product.Brand;
import model.Product.Category;
import view.Shop;

import java.util.Scanner;

public class BrandInput {

    final static Scanner scanner = new Scanner(System.in);

    public static boolean input (){

        System.out.println("Brand title");
        String title = scanner.nextLine();

        Brand brand = new Brand(title);

        BrandInputToDB.brandinput(brand);

        Shop s = new Shop();
        s.dashbord();
        return true;
    }
}
