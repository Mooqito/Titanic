package controller.product;

//import model.Product.product.ProductInputToDB;
import model.Product.product.ProductInputToDB;
import model.Product.brand.SelectBrand;
import model.Product.category.SelectCategory;
import model.Product.product.Product;
import model.Product.provider.SelectProvider;
import view.Shop;

import java.util.Scanner;

public class ProductInput {

    private final Scanner scanner = new Scanner(System.in);

    public Product ProductInputList() {

        System.out.println("title");
        String title = scanner.nextLine();

        System.out.println("price");
        long price = Long.parseLong(scanner.nextLine());


        System.out.println("description");
        String description = scanner.nextLine();

        System.out.println("category id");
        long category_id = SelectCategory.select();
        if (category_id==-1){
            System.out.println("امکان ادامه نیست. ابتدا دسته‌بندی تعریف کنید.");
            Shop s = new Shop();
            s.category();
        }

        System.out.println("provider id");
        long provider_id = SelectProvider.select();
        if (provider_id==-1){
            System.out.println("امکان ادامه نیست. ابتدا تامین کنندگاه را تعریف کنید.");
            Shop s = new Shop();
            s.provider();
        }

        System.out.println("Brand id");
        long brand_id = SelectBrand.select();
        if (brand_id==-1){
            System.out.println("امکان ادامه نیست. ابتدا برند تعریف کنید.");
            Shop s = new Shop();
            s.brand();
        }

        Product product = new Product(title,price,description,category_id,provider_id,brand_id);
        if (ProductInputToDB.productInput(product)){
            System.out.println("saccsess");
            Shop s = new Shop();
            s.product_management();
        }else {
            System.out.println("no sacc");
            Shop s = new Shop();
            s.product_management();
        }
        return product;
    }

}