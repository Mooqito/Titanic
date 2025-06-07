package view.Brand;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import model.Product.brand.Brand;
import model.Product.brand.GetAllBrand;
import model.Product.product.Product;
import model.Product.product.ReadAllproduct;

import java.util.List;
import java.util.stream.Collectors;

public class ShowBrandList {


    public VBox createShowBrandsForm() {
        // ایجاد ScrollPane برای قابلیت اسکرول
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefViewportHeight(500); // ارتفاع پیش‌فرض برای اسکرول

        VBox form = new VBox(15);
        form.setAlignment(Pos.TOP_RIGHT);
        form.setPadding(new Insets(10));

        List<Brand> brands = GetAllBrand.getAllBrand();
        List<Product> products = ReadAllproduct.Readproduct();

        for (Brand brand : brands) {
            TitledPane brandPane = new TitledPane();
            brandPane.setText(brand.getTitle());

            // فیلتر کردن محصولات این برند
            List<Product> brandProducts = products.stream()
                    .filter(product -> product.getBrandTitle().equals(brand.getTitle()))
                    .collect(Collectors.toList());

            if (brandProducts.isEmpty()) {
                Label emptyLabel = new Label("هیچ محصولی در این برند وجود ندارد");
                brandPane.setContent(emptyLabel);
            } else {
                // ایجاد جدول محصولات
                TableView<Product> table = new TableView<>();
                table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

                // ستون نام محصول
                TableColumn<Product, String> titleCol = new TableColumn<>("نام محصول");
                titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
                titleCol.setPrefWidth(150);// ستون قیمت
                TableColumn<Product, Long> priceCol = new TableColumn<>("قیمت");
                priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
                priceCol.setPrefWidth(100);
                // فرمت کردن قیمت با جداکننده هزارگان
                priceCol.setCellFactory(column -> new TableCell<Product, Long>() {
                    @Override
                    protected void updateItem(Long price, boolean empty) {
                        super.updateItem(price, empty);
                        if (empty || price == null) {
                            setText(null);
                        } else {
                            setText(String.format("%,d", price));
                        }
                    }
                });

                // ستون موجودی
                TableColumn<Product, Long> quantityCol = new TableColumn<>("موجودی");
                quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
                quantityCol.setPrefWidth(80);

                // ستون دسته‌بندی
                TableColumn<Product, String> categoryCol = new TableColumn<>("دسته‌بندی");
                categoryCol.setCellValueFactory(new PropertyValueFactory<>("categoryTitle"));
                categoryCol.setPrefWidth(120);

                // ستون توضیحات
                TableColumn<Product, String> descriptionCol = new TableColumn<>("توضیحات");
                descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
                descriptionCol.setPrefWidth(200);

                // ستون تامین‌کننده
                TableColumn<Product, String> providerCol = new TableColumn<>("تامین‌کننده");
                providerCol.setCellValueFactory(new PropertyValueFactory<>("providerTitle"));
                providerCol.setPrefWidth(120);

                // تنظیم راست به چپ برای ستون‌ها
                titleCol.setStyle("-fx-alignment: CENTER-RIGHT;");
                priceCol.setStyle("-fx-alignment: CENTER-RIGHT;");
                quantityCol.setStyle("-fx-alignment: CENTER-RIGHT;");
                categoryCol.setStyle("-fx-alignment: CENTER-RIGHT;");
                descriptionCol.setStyle("-fx-alignment: CENTER-RIGHT;");
                providerCol.setStyle("-fx-alignment: CENTER-RIGHT;");

                table.getColumns().addAll(
                        titleCol, priceCol, quantityCol, categoryCol,
                        providerCol, descriptionCol
                );

                // تنظیم داده‌های جدول
                table.setItems(FXCollections.observableArrayList(brandProducts));

                // تنظیم ارتفاع جدول بر اساس تعداد ردیف‌ها
                table.setPrefHeight(Math.min(brandProducts.size() * 30 + 40, 200));

                brandPane.setContent(table);
            }

            form.getChildren().add(brandPane);
        }

        scrollPane.setContent(form);

        // برگرداندن VBox اصلی که شامل ScrollPane است
        VBox mainContainer = new VBox(scrollPane);
        mainContainer.setPadding(new Insets(10));

        return mainContainer;
    }
}
