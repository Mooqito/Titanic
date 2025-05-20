package model;

import controller.product.Brand;
import controller.product.Category;
import controller.product.Product;
import controller.product.Provider;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProductInput {

    private final Scanner scanner = new Scanner(System.in);

    public void ProductInputList() {
        System.out.println("id");
        long id = Long.parseLong(scanner.nextLine());

        System.out.println("title");
        String title = scanner.next();

        System.out.println("price");
        long price = Long.parseLong(scanner.nextLine());


        System.out.println("description");
        String description = scanner.next();

        System.out.println("category title");
        String categoryTitle = scanner.next();
        Category category = new Category(categoryTitle);

        System.out.println("Brand title");
        String brandTitle = scanner.next();
        Brand brand = new Brand(brandTitle);

        System.out.println("provider title");
        int providerCount = scanner.nextInt();
        List<Provider> providers = new ArrayList<>();
        for (int i=1;i<=providerCount;i++){
            System.out.println("provider"+i+":");
            String providerTitle = scanner.next();
            providers.add(new Provider(providerTitle));
        }
        new Product(id,title,price,description,category,providers,brand);
    }
}