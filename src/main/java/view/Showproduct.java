package view;

import model.Product.Product;
import model.Product.ReadAllproduct;

import java.util.List;

public class Showproduct {

    public void show() {

        List<Product> products = ReadAllproduct.writeproduct();

        for (Product p : products) {
            System.out.println(p.getTitle());
        }
    }
}
