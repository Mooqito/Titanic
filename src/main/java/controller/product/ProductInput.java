package controller.product;

import model.Product.Brand;
import model.Product.Category;
import model.Product.Product;
import model.Product.Provider;

import java.util.ArrayList;
import java.util.List;
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
        int providerCount = Integer.parseInt(scanner.nextLine());
        List<Provider> providers = new ArrayList<>();
        for (int i=1;i<=providerCount;i++){
            System.out.println("provider"+i+":");
            String providerTitle = scanner.nextLine();
            providers.add(new Provider(providerTitle));
        }
        return  new Product(id,title,price,description,category,providers,brand);
    }
}