package view;

import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Product.brand.Brand;
import model.Product.brand.DeleteBrand;
import model.Product.brand.GetAllBrand;
import model.Product.category.CategoruInputToDB;
import model.Product.category.Category;
import model.Product.category.GetAllCategory;
import model.Product.product.Product;
import model.Product.product.ReadAllproduct;
import runner.Main;

import java.util.List;
import java.util.stream.Collectors;


public class CategoryManagement extends VBox{

    private VBox contentArea;
    private ListView<String> categoryListView;

    public CategoryManagement (VBox contentArea){
        super(5);
        this.contentArea = contentArea;
        setVisible(false);
        setManaged(false);
        setStyle("-fx-padding:0 20 0 0;");
        setAlignment(Pos.TOP_RIGHT);
    }

    private void createCategoryMenu() {

        String buttnStyle = "-fx-alignment:CENTER_RIGHT;    -fx-padding:5 10 5 5;   -fx-content-display: RIGHT;";
        Button addBut = new Button("افزودن و حذف دسته بندی");
        Button showBut = new Button("نمایش برند‌ها");

        addBut.setStyle(buttnStyle);
        showBut.setStyle(buttnStyle);

        addBut.maxWidth(Double.MAX_VALUE);
        showBut.maxWidth(Double.MAX_VALUE);

        addBut.setOnAction(e -> openCategoryWindow("افزودن و حذف دسته بندی"));
        showBut.setOnAction(e -> openCategoryWindow("نمایش دسته بندی"));

        getChildren().addAll(addBut,showBut);
    }

    public void openCategoryWindow(String title) {
        Stage stage = new Stage();
        stage.setTitle(title);

        VBox content = new VBox(10);
        content.setAlignment(Pos.CENTER_RIGHT);
        content.setPadding(new Insets(20));
        content.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);

        if(title.equals("افزودن و حذف دسته بندی")) {
            content.getChildren().add(createAddDeleteCategoryForm());
        } else if (title.equals("نمایش دسته بندی")) {
//            content.getChildren().add(createAddDeleteCategoryForm());
        }

        Button backBut = new Button("بازگشت");
        backBut.setStyle("-fx-alignment: CENTER_RIGHT;-fx-padding: 5 10 5 5");
        backBut.setOnAction(e -> stage.close());
        content.getChildren().add(backBut);

        Scene scene = new Scene(content,600,400);
        stage.setScene(scene);
        stage.show();
    }

    private VBox createAddDeleteCategoryForm() {
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
        List<Brand> brands = GetAllBrand.getAllBrand();
        ObservableList<String> brandNames = FXCollections.observableArrayList(
                brands.stream()
                        .map(Brand::getTitle)
                        .collect(Collectors.toList())
        );
        listView.setItems(brandNames);
    }
}
