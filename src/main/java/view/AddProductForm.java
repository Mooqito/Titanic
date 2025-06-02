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
import model.Product.provider.GetAllProvider;
import model.Product.provider.ProviderInputToDB;
import view.validation.ProductValidation;

import java.util.List;

public class AddProductForm {
    private VBox content;
    private ComboBox<String> categoryComboBox;
    private ComboBox<String> supplierComboBox;
    private ComboBox<String> brandComboBox;
    private TextField nameField;
    private TextField priceField;
    private TextArea descriptionArea;
    private TextField quantityField;

    public AddProductForm() {
        createContent();
    }

    private void createContent() {
        content = new VBox(15);
        content.setAlignment(Pos.CENTER_RIGHT);
        content.setPadding(new Insets(20));
        content.setMaxWidth(600);

        // فیلدهای ورودی با اعتبارسنجی
        nameField = new TextField();
        nameField.setPromptText("نام محصول");
        nameField.setTextFormatter(ProductValidation.createTitleFormatter());

        priceField = new TextField();
        priceField.setPromptText("قیمت");
        priceField.setTextFormatter(ProductValidation.createPriceFormatter());

        descriptionArea = new TextArea();
        descriptionArea.setPromptText("توضیحات");
        descriptionArea.setPrefRowCount(3);
        descriptionArea.setTextFormatter(ProductValidation.createDescriptionFormatter());

        // کامبوباکس‌ها با دکمه‌های اضافه کردن
        HBox categoryBox = createComboWithAddButton("دسته‌بندی", categoryComboBox = new ComboBox<>());
        HBox supplierBox = createComboWithAddButton("تامین کننده", supplierComboBox = new ComboBox<>());
        HBox brandBox = createComboWithAddButton("برند", brandComboBox = new ComboBox<>());

        quantityField = new TextField();
        quantityField.setPromptText("تعداد");
        quantityField.setTextFormatter(ProductValidation.createQuantityFormatter());

        // بارگذاری داده‌های کامبوباکس‌ها
        loadComboBoxData();

        // دکمه ثبت
        Button submitButton = new Button("ثبت محصول");
        submitButton.setMaxWidth(200);

        submitButton.setOnAction(e -> {
            if (validateFields()) {
                String name = nameField.getText();
                long price = Long.parseLong(priceField.getText());
                String description = descriptionArea.getText();
                String category = categoryComboBox.getValue();
                String supplier = supplierComboBox.getValue();
                String brand = brandComboBox.getValue();
                int quantity = Integer.parseInt(quantityField.getText());

                // بررسی وجود محصول
                if (ProductInputToDB.productExist(name, price, category, supplier, brand)) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("محصول موجود");
                    alert.setHeaderText(null);
                    alert.setContentText("محصول با این مشخصات وجود دارد. آیا می‌خواهید تعداد آن را افزایش دهید؟");

                    alert.showAndWait().ifPresent(response -> {
                        if (response == ButtonType.OK) {
                            ProductInputToDB.updateProductQuantity(name, price, category, supplier, brand, quantity);
                            showSuccessMessage("تعداد محصول با موفقیت به‌روزرسانی شد");
                            clearFields();
                        }
                    });
                } else {
                    // افزودن محصول جدید
                    if (ProductInputToDB.productInput(name, price, description, category, supplier, brand, quantity)) {
                        showSuccessMessage("محصول با موفقیت ثبت شد");
                        clearFields();
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
                        success = ProviderInputToDB.providerinput(result);
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
        List<String> provider = GetAllProvider.provider();
        supplierComboBox.setItems(FXCollections.observableArrayList(provider));
        List<String> brands = GetAllBrand.brand();
        brandComboBox.setItems(FXCollections.observableArrayList(brands));
    }

    private boolean validateFields() {
        String validationError = ProductValidation.validateForm(
                nameField.getText(),
                priceField.getText(),
                descriptionArea.getText(),
                categoryComboBox.getValue(),
                brandComboBox.getValue(),
                supplierComboBox.getValue(),
                quantityField.getText()
        );

        if (validationError != null) {
            showErrorMessage(validationError);
            return false;
        }

        return true;
    }

    private void clearFields() {
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