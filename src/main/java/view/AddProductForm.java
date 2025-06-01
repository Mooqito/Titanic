package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.collections.FXCollections;
import model.Product.brand.Brand;
import model.Product.brand.BrandInputToDB;
import model.Product.brand.GetAllBrand;
import model.Product.category.CategoruInputToDB;
import model.Product.category.GetAllCategory;
import model.Product.product.ProductInputToDB;

import java.util.List;

public class AddProductForm {
    private VBox content;
    private ComboBox<String> categoryComboBox;
    private ComboBox<String> supplierComboBox;
    private ComboBox<String> brandComboBox;

    public AddProductForm() {
        createContent();
    }

    private void createContent() {
        content = new VBox(15);
        content.setAlignment(Pos.CENTER_RIGHT);
        content.setPadding(new Insets(20));
        content.setMaxWidth(600);

        // فیلدهای ورودی
        TextField nameField = new TextField();
        nameField.setPromptText("نام محصول");

        TextField priceField = new TextField();
        priceField.setPromptText("قیمت");

        TextArea descriptionArea = new TextArea();
        descriptionArea.setPromptText("توضیحات");
        descriptionArea.setPrefRowCount(3);

        // کامبوباکس‌ها با دکمه‌های اضافه کردن
        HBox categoryBox = createComboWithAddButton("دسته‌بندی", categoryComboBox = new ComboBox<>());
        HBox supplierBox = createComboWithAddButton("تامین کننده", supplierComboBox = new ComboBox<>());
        HBox brandBox = createComboWithAddButton("برند", brandComboBox = new ComboBox<>());

        TextField quantityField = new TextField();
        quantityField.setPromptText("تعداد");

        // بارگذاری داده‌های کامبوباکس‌ها
        loadComboBoxData();

        // دکمه ثبت
        Button submitButton = new Button("ثبت محصول");
        submitButton.setMaxWidth(200);

        submitButton.setOnAction(e -> {
            if (validateFields(nameField, priceField, quantityField)) {
                String name = nameField.getText();
                double price = Double.parseDouble(priceField.getText());
                String description = descriptionArea.getText();
                String category = categoryComboBox.getValue();
                String supplier = supplierComboBox.getValue();
                String brand = brandComboBox.getValue();
                int quantity = Integer.parseInt(quantityField.getText());

                // بررسی وجود محصول
                if (ProductInputToDB.productExist(name, (long) price, category, supplier, brand)) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("محصول موجود");
                    alert.setHeaderText(null);
                    alert.setContentText("محصول با این مشخصات وجود دارد. آیا می‌خواهید تعداد آن را افزایش دهید؟");

                    alert.showAndWait().ifPresent(response -> {
                        if (response == ButtonType.OK) {
                            ProductInputToDB.updateProductQuantity(name, (long) price, category, supplier, brand, quantity);
                            showSuccessMessage("تعداد محصول با موفقیت به‌روزرسانی شد");
                            clearFields(nameField, priceField, descriptionArea, quantityField);
                        }
                    });
                } else {
                    // افزودن محصول جدید
                    if (ProductInputToDB.productInput(name, (long) price, description, category, supplier, brand, quantity)) {
                        showSuccessMessage("محصول با موفقیت ثبت شد");
                        clearFields(nameField, priceField, descriptionArea, quantityField);
                    } else {
                        showErrorMessage("خطا در ثبت محصول");
                    }
                }
            }
        });

        // اضافه کردن فیلدها به فرم
        content.getChildren().addAll(
                createLabeledField("نام محصول:", nameField),
                createLabeledField("قیمت:", priceField),
                createLabeledField("توضیحات:", descriptionArea),
                categoryBox,
                supplierBox,
                brandBox,
                createLabeledField("تعداد:", quantityField),
                submitButton
        );
    }

    private HBox createLabeledField(String labelText, Control field) {
        HBox box = new HBox(10);
        box.setAlignment(Pos.CENTER_RIGHT);

        Label label = new Label(labelText);
        label.setMinWidth(100);
        label.setAlignment(Pos.CENTER_RIGHT);

        field.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(field, Priority.ALWAYS);

        box.getChildren().addAll(field, label);
        return box;
    }

    private HBox createComboWithAddButton(String labelText, ComboBox<String> comboBox) {
        HBox box = new HBox(10);
        box.setAlignment(Pos.CENTER_RIGHT);

        Button addButton = new Button("+");
        addButton.setOnAction(e -> showAddDialog(labelText, comboBox));

        Label label = new Label(labelText + ":");
        label.setMinWidth(100);
        label.setAlignment(Pos.CENTER_RIGHT);

        comboBox.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(comboBox, Priority.ALWAYS);

        box.getChildren().addAll(addButton, comboBox, label);
        return box;
    }

    private void showAddDialog(String type, ComboBox<String> comboBox) {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("افزودن " + type);
        dialog.setHeaderText(null);

        ButtonType addButtonType = new ButtonType("افزودن", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField nameField = new TextField();
        nameField.setPromptText("نام " + type);
        grid.add(new Label("نام " + type + ":"), 0, 0);
        grid.add(nameField, 1, 0);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButtonType) {
                return nameField.getText();
            }
            return null;
        });

        dialog.showAndWait().ifPresent(result -> {
            if (!result.isEmpty()) {
                // افزودن به دیتابیس بر اساس نوع
                boolean success = false;
                switch (type) {
                    case "دسته‌بندی":
                        success = CategoruInputToDB.categoryInput(result);
                        break;
                    case "تامین کننده":
//                        success = DatabaseManager.addSupplier(result);
                        break;
                    case "برند":
                        success = BrandInputToDB.brandinput(result);
                        break;
                }

                if (success) {
                    comboBox.getItems().add(result);
                    comboBox.setValue(result);
                    showSuccessMessage(type + " با موفقیت اضافه شد");
                } else {
                    showErrorMessage("خطا در افزودن " + type);
                }
            }
        });
    }

    private void loadComboBoxData() {
        // بارگذاری داده‌ها از دیتابیس
        List<String> category = GetAllCategory.category();
        categoryComboBox.setItems(FXCollections.observableArrayList(category));
//        supplierComboBox.setItems(FXCollections.observableArrayList(DatabaseManager.getSuppliers()));
        List<String> brands = GetAllBrand.brand();
        brandComboBox.setItems(FXCollections.observableArrayList(brands));
    }

    private boolean validateFields(TextField nameField, TextField priceField, TextField quantityField) {
        if (nameField.getText().isEmpty() || priceField.getText().isEmpty() ||
                categoryComboBox.getValue() == null || supplierComboBox.getValue() == null ||
                brandComboBox.getValue() == null || quantityField.getText().isEmpty()) {
            showErrorMessage("لطفاً تمام فیلدهای ضروری را پر کنید");
            return false;
        }

        try {
            Double.parseDouble(priceField.getText());
            Integer.parseInt(quantityField.getText());
        } catch (NumberFormatException e) {
            showErrorMessage("لطفاً قیمت و تعداد را به صورت عددی وارد کنید");
            return false;
        }

        return true;
    }

    private void clearFields(TextField nameField, TextField priceField,
                             TextArea descriptionArea, TextField quantityField) {
        nameField.clear();
        priceField.clear();
        descriptionArea.clear();
        quantityField.clear();
        categoryComboBox.setValue(null);
        supplierComboBox.setValue(null);
        brandComboBox.setValue(null);
    }

    private void showSuccessMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("موفقیت");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showErrorMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("خطا");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public VBox getContent() {
        return content;
    }
}