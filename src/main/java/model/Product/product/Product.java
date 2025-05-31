package model.Product.product;

public class Product {

    private long id;
    private String title;
    private long price;
    private String description;

    // Fields for creating new products
    private long category_id;
    private long providers_id;
    private long brand_id;

    // Fields for displaying titles
    private String categoryTitle;
    private String brandTitle;
    private String providerTitle;

    private long Quantity;
    // Constructor for creating new products (with IDs)
    public Product(String title, long price, String description, long category_id, long providers_id, long brand_id,long Quantity) {
        this.title = title;
        this.price = price;
        this.description = description;
        this.category_id = category_id;
        this.providers_id = providers_id;
        this.brand_id = brand_id;
        this.Quantity = Quantity;
    }

    public Product(long id, String title, long price, String description, long category_id, long providers_id, long brand_id,long Quantity) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.description = description;
        this.category_id = category_id;
        this.providers_id = providers_id;
        this.brand_id = brand_id;
        this.Quantity = Quantity;
    }

    public long getQuantity() {
        return Quantity;
    }

    public void setQuantity(long quantity) {
        Quantity = quantity;
    }

    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getCategory_id() {
        return category_id;
    }

    public void setCategory_id(long category_id) {
        this.category_id = category_id;
    }

    public long getProviders_id() {
        return providers_id;
    }

    public void setProviders_id(long providers_id) {
        this.providers_id = providers_id;
    }

    public long getBrand_id() {
        return brand_id;
    }

    public void setBrand_id(long brand_id) {
        this.brand_id = brand_id;
    }

    public String getCategoryTitle() {
        return categoryTitle;
    }

    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }

    public String getBrandTitle() {
        return brandTitle;
    }

    public void setBrandTitle(String brandTitle) {
        this.brandTitle = brandTitle;
    }

    public String getProviderTitle() {
        return providerTitle;
    }

    public void setProviderTitle(String providerTitle) {
        this.providerTitle = providerTitle;
    }

}

