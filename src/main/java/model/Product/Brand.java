package model.Product;

public class Brand {
    private long id;
    private String title;

    public Brand(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
