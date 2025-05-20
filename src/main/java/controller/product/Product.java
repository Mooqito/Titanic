package controller.product;

import java.util.List;

public class Product {

    private long id;
    private String title;
    private long price;
    private String description;

    private Category category;
    private List<Provider> providers;
}
