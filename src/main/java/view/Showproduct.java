package view;

import model.Product.Product;
import model.Product.ReadAllproduct;

import java.util.List;

public class Showproduct {

    public void show() {
        List<Product> products = ReadAllproduct.Readproduct();

        for (Product p : products) {
            System.out.println(" Title : " + p.getTitle() + 
                "\n price : " + p.getPrice() +
                "\n description : " + p.getDescription() +
                "\n category : " + p.getCategory_id() +
                "\n brand : " + p.getBrand_id() +
                "\n provider : " + p.getProviders_id());
        }
    }
}
