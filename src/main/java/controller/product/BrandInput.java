package controller.product;

import model.Product.brand.BrandInputToDB;

import java.util.Scanner;

public class BrandInput {

    final static Scanner scanner = new Scanner(System.in);

    public static boolean input (){

        System.out.println("Brand title");
        String title = scanner.nextLine();

        BrandInputToDB.brandinput(title);


        return true;
    }
}
