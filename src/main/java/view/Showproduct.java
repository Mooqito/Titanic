package view;

import model.Product.Product;
import model.Product.ReadAllproduct;

import java.util.List;

public class Showproduct {

    public void show() {

        List<Product> products = ReadAllproduct.Readproduct();

        for (Product p : products) {

            System.out.println("product "+p.getId()+" : \n"+" Title : "+p.getTitle()+"\n price : "+p.getPrice()
                +"\n description : "+p.getDescription()+"\n category : "+p.getCategory().getTitle()
                    +"\n brand : "+p.getBrand().getTitle()+"\n provider : "+p.getProviders().getTitle());
        }
    }
}
