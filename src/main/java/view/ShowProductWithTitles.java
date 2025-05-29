package view;

import model.Product.Product;
import model.Product.ReadAllproduct;

import java.util.List;

public class ShowProductWithTitles {

    public void show() {
        List<Product> products = ReadAllproduct.Readproduct();

        for (Product p : products) {
            System.out.println("----------------------------------------");
            System.out.println(" عنوان محصول: " + p.getTitle());
            System.out.println(" قیمت: " + p.getPrice());
            System.out.println(" توضیحات: " + p.getDescription());
            System.out.println(" دسته‌بندی: " + (p.getCategoryTitle() != null ? p.getCategoryTitle() : "نامشخص"));
            System.out.println(" برند: " + (p.getBrandTitle() != null ? p.getBrandTitle() : "نامشخص"));
            System.out.println(" تامین‌کننده: " + (p.getProviderTitle() != null ? p.getProviderTitle() : "نامشخص"));
            System.out.println("----------------------------------------");
        }
    }
}