package controller.product;

import model.Product.provider.Provider;

import java.util.Scanner;

public class PrividerInput {

    final static Scanner scanner = new Scanner(System.in);

    public static boolean input (){

        System.out.println("Brand title");
        String title = scanner.nextLine();

        Provider provider = new Provider(title);

//        ProviderInputToDB.providerinput(provider);
        Shop s = new Shop();
        s.dashbord();
        return true;
    }
}
