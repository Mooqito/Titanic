package controller.product;

//import database.ProductInputToDB;
import database.ProductInputToDB;
import model.Product.Brand;
import model.Product.Category;
import model.Product.Product;
import model.Product.Provider;
import view.Dashboard;

import java.util.Scanner;

public class ProductInput {

    private final Scanner scanner = new Scanner(System.in);

    public Product ProductInputList() {
        System.out.println("id");
        long id = Long.parseLong(scanner.nextLine());

        System.out.println("title");
        String title = scanner.nextLine();

        System.out.println("price");
        long price = Long.parseLong(scanner.nextLine());


        System.out.println("description");
        String description = scanner.nextLine();

        System.out.println("category title");
        String categoryTitle = scanner.nextLine();
        Category category = new Category(categoryTitle);

        System.out.println("Brand title");
        String brandTitle = scanner.nextLine();
        Brand brand = new Brand(brandTitle);

        System.out.println("provider title");
        String providerTitle = scanner.nextLine();
        Provider providers = new Provider(providerTitle);


        Product product = new Product(id,title,price,description,category,providers,brand);
        if (ProductInputToDB.productInput(product)){
            System.out.println("saccsess");
            new Dashboard();
        }else {
            System.out.println("no sacc");
            new Dashboard();
        }
        return product;
    }
}