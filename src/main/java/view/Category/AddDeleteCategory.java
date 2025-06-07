package view.Category;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import model.Product.brand.DeleteBrand;
import model.Product.category.CategoruInputToDB;
import model.Product.category.Category;
import model.Product.category.GetAllCategory;
import model.Product.product.Product;
import model.Product.product.ReadAllproduct;
import runner.Main;

import java.util.List;
import java.util.stream.Collectors;

public class AddDeleteCategory {
    private ListView<String> categoryListView;

    public VBox createAddDeleteCategoryForm() {
        VBox form = new VBox(15);
        form.setAlignment(Pos.TOP_RIGHT);

        Label label = new Label("افزودن دسته بندی جدید");
        label.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        TextField textField = new TextField();
        textField.setPromptText("نام دسته بندی را وارد کنید");
        textField.setMaxWidth(300);

        Button button = new Button("افزودن");
        button.setOnAction(e -> {
            String categoryName = textField.getText().trim();
            if (!categoryName.isEmpty()) {
                List<Category> list = GetAllCategory.getAllCategory();
                boolean isDuplicate = list.stream()
                        .anyMatch(brand -> brand.getTitle().equalsIgnoreCase(categoryName));
                if (isDuplicate) {
                    Main.showAlert("خطا", "این دسته بندی قبلاً ثبت شده است!");
                } else {
                    Category category = new Category(categoryName);
                    CategoruInputToDB.categoryInput(categoryName);
                    Main.showAlert("موفقیت", "دسته بندی با موفقیت اضافه شد.");
                    textField.clear();
                    updateCategoryList(categoryListView);
                }
            } else {
                Main.showAlert("خطا", "لطفاً نام برند را وارد کنید.");
            }
        });

        Label listLabel = new Label("لیست دسته بتدی ها");
        listLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        categoryListView = new ListView<>();
        categoryListView.setPrefHeight(200);
        updateCategoryList(categoryListView);

        Button deleteBut = new Button("حذف برند انتخاب شده");
        deleteBut.setStyle("-fx-background-color: #ff4444; -fx-text-fill: white;");
        deleteBut.setDisable(true);

        categoryListView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            deleteBut.setDisable(newSelection == null);
        });
        deleteBut.setOnAction(e ->{
            String selectCategory = categoryListView.getSelectionModel().getSelectedItem();
            if (selectCategory != null) {
                List<Product> list = ReadAllproduct.Readproduct();
                boolean isUsed = list.stream().anyMatch(product -> product.getCategoryTitle().equals(selectCategory));

                if (isUsed) {
                    Main.showAlert("خطا", "این برند در محصولات استفاده شده است و نمی‌توان آن را حذف کرد.");
                } else {
                    DeleteBrand.deleteBrand(selectCategory);
                    Main.showAlert("موفقیت", "برند با موفقیت حذف شد.");
                    updateCategoryList(categoryListView);
                }
            }
        });

        form.getChildren().addAll(
                label,
                textField,
                button,
                new Separator(),
                listLabel,
                categoryListView,
                deleteBut
        );

        return form;
    }
    private void updateCategoryList(ListView<String> listView) {
        List<Category> category = GetAllCategory.getAllCategory();
        ObservableList<String> brandNames = FXCollections.observableArrayList(
                category.stream()
                        .map(Category::getTitle)
                        .collect(Collectors.toList())
        );
        listView.setItems(brandNames);
    }
}
