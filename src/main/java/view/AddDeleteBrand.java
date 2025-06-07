package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import model.Product.brand.Brand;
import model.Product.brand.BrandInputToDB;
import model.Product.brand.DeleteBrand;
import model.Product.brand.GetAllBrand;
import model.Product.product.Product;
import model.Product.product.ReadAllproduct;
import runner.Main;

import java.util.List;
import java.util.stream.Collectors;

public class AddDeleteBrand {
    private ListView<String> brandListView;


    public VBox createAddDeleteBrandForm() {
        VBox form = new VBox(15);
        form.setAlignment(Pos.TOP_RIGHT);

        // بخش افزودن برند
        Label addLabel = new Label("افزودن برند جدید");
        addLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        TextField brandNameField = new TextField();
        brandNameField.setPromptText("نام برند را وارد کنید");
        brandNameField.setMaxWidth(300);

        Button addButton = new Button("افزودن");
        addButton.setOnAction(e -> {
            String brandName = brandNameField.getText().trim();
            if (!brandName.isEmpty()) {
                // بررسی تکراری نبودن برند
                List<Brand> existingBrands = GetAllBrand.getAllBrand();
                boolean isDuplicate = existingBrands.stream()
                        .anyMatch(brand -> brand.getTitle().equalsIgnoreCase(brandName));

                if (isDuplicate) {
                    Main.showAlert("خطا", "این برند قبلاً ثبت شده است!");
                } else {
                    Brand newBrand = new Brand(brandName);
                    BrandInputToDB.brandInput(newBrand);
                    Main.showAlert("موفقیت", "برند با موفقیت اضافه شد.");
                    brandNameField.clear();
                    updateBrandList(brandListView); // بروزرسانی لیست
                }
            } else {
                Main.showAlert("خطا", "لطفاً نام برند را وارد کنید.");
            }
        });// بخش نمایش و حذف برند‌ها
        Label listLabel = new Label("لیست برند‌ها");
        listLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        brandListView = new ListView<>();
        brandListView.setPrefHeight(200);
        updateBrandList(brandListView);

        Button deleteButton = new Button("حذف برند انتخاب شده");
        deleteButton.setStyle("-fx-background-color: #ff4444; -fx-text-fill: white;");
        deleteButton.setDisable(true);

        brandListView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            deleteButton.setDisable(newSelection == null);
        });

        deleteButton.setOnAction(e -> {
            String selectedBrand = brandListView.getSelectionModel().getSelectedItem();
            if (selectedBrand != null) {
                // بررسی استفاده از برند در محصولات
                List<Product> products = ReadAllproduct.Readproduct();
                boolean isUsed = products.stream()
                        .anyMatch(product -> product.getBrandTitle().equals(selectedBrand));

                if (isUsed) {
                    Main.showAlert("خطا", "این برند در محصولات استفاده شده است و نمی‌توان آن را حذف کرد.");
                } else {
                    DeleteBrand.deleteBrand(selectedBrand);
                    Main.showAlert("موفقیت", "برند با موفقیت حذف شد.");
                    updateBrandList(brandListView);
                }
            }
        });

        form.getChildren().addAll(
                addLabel,
                brandNameField,
                addButton,
                new Separator(),
                listLabel,
                brandListView,
                deleteButton
        );

        return form;
    }

    private void updateBrandList(ListView<String> listView) {
        List<Brand> brands = GetAllBrand.getAllBrand();
        ObservableList<String> brandNames = FXCollections.observableArrayList(
                brands.stream()
                        .map(Brand::getTitle)
                        .collect(Collectors.toList())
        );
        listView.setItems(brandNames);
    }
}
