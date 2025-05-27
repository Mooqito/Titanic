package controller.product;

import database.CategoruInputToDB;
import model.Product.Category;
import view.Shop;

import java.util.Scanner;

public class Categoryinput {

    final static Scanner scanner = new Scanner(System.in);

    public static boolean input (){

        System.out.println("category title");
        String title = scanner.nextLine();

        Category category = new Category(title);

        CategoruInputToDB.categoryInput(category);
        Shop s = new Shop();
        s.dashbord();

        return true;
    }
}
