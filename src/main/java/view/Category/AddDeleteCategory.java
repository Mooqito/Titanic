package view.Category;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Priority;
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
        label.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: white;");

        TextField textField = new TextField();
        textField.setPromptText("نام دسته بندی را وارد کنید");
        textField.setMaxWidth(300);
        textField.setStyle("-fx-text-fill: white; -fx-prompt-text-fill: white; -fx-alignment: center-right; -fx-font-size: 14px; -fx-background-color: rgba(175, 180, 204, 0.58); -fx-background-radius: 5;");
        textField.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);

        // Add status message
        Label addStatusLabel = new Label("");
        addStatusLabel.setStyle("-fx-font-size: 13px;");

        Button button = new Button("افزودن");
        button.setStyle("-fx-background-color: rgb(2, 75, 2); -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8 15; -fx-background-radius: 5;");
        button.setOnAction(e -> {
            String categoryName = textField.getText().trim();
            if (!categoryName.isEmpty()) {
                List<Category> list = GetAllCategory.getAllCategory();
                boolean isDuplicate = list.stream()
                        .anyMatch(brand -> brand.getTitle().equalsIgnoreCase(categoryName));
                if (isDuplicate) {
                    addStatusLabel.setText("این دسته بندی قبلاً ثبت شده است!");
                    addStatusLabel.setStyle("-fx-text-fill: #7a0000; -fx-font-size: 13px;");
                } else {
                    Category category = new Category(categoryName);
                    CategoruInputToDB.categoryInput(categoryName);
                    addStatusLabel.setText("دسته بندی با موفقیت اضافه شد.");
                    addStatusLabel.setStyle("-fx-text-fill: #00ba2d; -fx-font-size: 13px;");
                    textField.clear();
                    updateCategoryList(categoryListView);
                }
                // Hide message after 3 seconds
                new javafx.animation.Timeline(
                        new javafx.animation.KeyFrame(
                                javafx.util.Duration.seconds(3),
                                ae -> addStatusLabel.setText("")
                        )
                ).play();
            } else {
                addStatusLabel.setText("لطفاً نام دسته بندی را وارد کنید.");
                addStatusLabel.setStyle("-fx-text-fill: #7a0000; -fx-font-size: 13px;");
                new javafx.animation.Timeline(
                        new javafx.animation.KeyFrame(
                                javafx.util.Duration.seconds(3),
                                ae -> addStatusLabel.setText("")
                        )
                ).play();
            }
        });

        HBox addBox = new HBox(10, addStatusLabel, button);
        addBox.setAlignment(Pos.CENTER_RIGHT);

        Label listLabel = new Label("لیست دسته بندی ها");
        listLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: white;");

        categoryListView = new ListView<>();
        categoryListView.setPrefHeight(200);
        VBox.setVgrow(categoryListView, Priority.ALWAYS);
        categoryListView.setStyle("-fx-text-fill: black; -fx-font-size: 14px; -fx-background-color: white; -fx-background-radius: 5;");
        categoryListView.setCellFactory(lv -> new ListCell<String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item);
                    setStyle("-fx-text-fill: black; -fx-alignment: center-right;");
                }
            }
        });
        updateCategoryList(categoryListView);

        // Delete status message
        Label deleteStatusLabel = new Label("");
        deleteStatusLabel.setStyle("-fx-font-size: 13px;");

        Button deleteBut = new Button("حذف دسته بندی انتخاب شده");
        deleteBut.setStyle("-fx-background-color: #ff4444; -fx-text-fill: white;");
        deleteBut.setDisable(true);

        HBox deleteBox = new HBox(10, deleteBut, deleteStatusLabel);
        deleteBox.setAlignment(Pos.CENTER_LEFT);

        categoryListView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            deleteBut.setDisable(newSelection == null);
        });
        deleteBut.setOnAction(e ->{
            String selectCategory = categoryListView.getSelectionModel().getSelectedItem();
            if (selectCategory != null) {
                List<Product> list = ReadAllproduct.Readproduct();
                boolean isUsed = list.stream().anyMatch(product -> product.getCategoryTitle().equals(selectCategory));

                if (isUsed) {
                    deleteStatusLabel.setText("این دسته بندی در محصولات استفاده شده است و نمی‌توان آن را حذف کرد.");
                    deleteStatusLabel.setStyle("-fx-text-fill: #7a0000; -fx-font-size: 13px;");
                } else {
                    DeleteBrand.deleteBrand(selectCategory);
                    deleteStatusLabel.setText("دسته بندی با موفقیت حذف شد.");
                    deleteStatusLabel.setStyle("-fx-text-fill: #00ba2d; -fx-font-size: 13px;");
                    updateCategoryList(categoryListView);
                }
                // Hide message after 3 seconds
                new javafx.animation.Timeline(
                        new javafx.animation.KeyFrame(
                                javafx.util.Duration.seconds(3),
                                ae -> deleteStatusLabel.setText("")
                        )
                ).play();
            }
        });

        form.getChildren().addAll(
                label,
                textField,
                addBox,
                new Separator(),
                listLabel,
                categoryListView,
                deleteBox
        );

        // Create a container to center the form
        VBox container = new VBox();
        container.setAlignment(Pos.CENTER);
        VBox.setVgrow(container, Priority.ALWAYS);
        container.getChildren().add(form);

        return container;
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
