package controller.product;

import model.Product.category.CategoruInputToDB;
import model.Product.category.Category;
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
