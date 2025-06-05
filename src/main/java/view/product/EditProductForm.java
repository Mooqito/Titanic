package view.product;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.collections.FXCollections;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Product.brand.GetAllBrand;
import model.Product.category.GetAllCategory;
import model.Product.product.Product;
import model.Product.product.ReadAllproduct;
import model.Product.product.UpdateProduct;
import model.Product.provider.GetAllProvider;

import java.util.List;

public class EditProductForm {
    private VBox content;
    private TableView<Product> productTable;
    private VBox editForm;
    private TextField nameField;
    private TextField priceField;
    private TextArea descriptionArea;
    private ComboBox<String> categoryComboBox;
    private ComboBox<String> supplierComboBox;
    private ComboBox<String> brandComboBox;
    private TextField quantityField;
    private Label titleLabel;

    public EditProductForm() {
        createContent();
    }

    private void createContent() {
        content = new VBox(15);
        content.setAlignment(Pos.CENTER_RIGHT);
        content.setPadding(new Insets(20));
        content.setMaxWidth(800);

        titleLabel = new Label("لیست محصولات");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        createProductTable();
        createEditForm();

        // در ابتدا فرم ویرایش را مخفی می‌کنیم
        editForm.setVisible(false);
        editForm.setManaged(false);

        content.getChildren().addAll(titleLabel, productTable, editForm);
    }

    private void createProductTable() {
        productTable = new TableView<>();
        productTable.setStyle("-fx-alignment: CENTER-RIGHT;");
        productTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // تعریف ستون‌ها
        TableColumn<Product, String> titleCol = new TableColumn<>("نام محصول");
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<Product, Long> priceCol = new TableColumn<>("قیمت");
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<Product, String> descriptionCol = new TableColumn<>("توضیحات");
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));

        TableColumn<Product, String> categoryCol = new TableColumn<>("دسته‌بندی");
        categoryCol.setCellValueFactory(new PropertyValueFactory<>("categoryTitle"));

        TableColumn<Product, String> brandCol = new TableColumn<>("برند");
        brandCol.setCellValueFactory(new PropertyValueFactory<>("brandTitle"));

        TableColumn<Product, String> providerCol = new TableColumn<>("تامین کننده");
        providerCol.setCellValueFactory(new PropertyValueFactory<>("providerTitle"));

        TableColumn<Product, Long> quantityCol = new TableColumn<>("تعداد");
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        // تنظیم راست به چپ برای همه ستون‌ها
        titleCol.setStyle("-fx-alignment: CENTER-RIGHT;");
        priceCol.setStyle("-fx-alignment: CENTER-RIGHT;");
        descriptionCol.setStyle("-fx-alignment: CENTER-RIGHT;");
        categoryCol.setStyle("-fx-alignment: CENTER-RIGHT;");
        brandCol.setStyle("-fx-alignment: CENTER-RIGHT;");
        providerCol.setStyle("-fx-alignment: CENTER-RIGHT;");
        quantityCol.setStyle("-fx-alignment: CENTER-RIGHT;");

        productTable.getColumns().addAll(
                titleCol, priceCol, descriptionCol, categoryCol,
                brandCol, providerCol, quantityCol
        );

        // بارگذاری داده‌ها
        loadTableData();

        // رویداد انتخاب محصول
        productTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                System.out.println("Selected product: " + newSelection.getTitle());
                loadProductToForm(newSelection);
                showEditForm();
            }
        });

        productTable.setPrefHeight(300);
    }

    private void showEditForm() {
        // مخفی کردن جدول و تغییر عنوان
        titleLabel.setText("ویرایش محصول");
        productTable.setVisible(false);
        productTable.setManaged(false);

        // نمایش فرم ویرایش
        editForm.setVisible(true);
        editForm.setManaged(true);
    }

    private void showProductTable() {
        // نمایش مجدد جدول و تغییر عنوان
        titleLabel.setText("لیست محصولات");
        productTable.setVisible(true);
        productTable.setManaged(true);

        // مخفی کردن فرم ویرایش
        editForm.setVisible(false);
        editForm.setManaged(false);

        // پاک کردن انتخاب در جدول
        productTable.getSelectionModel().clearSelection();

        // بارگذاری مجدد داده‌های جدول
        loadTableData();
    }

    private void createEditForm() {
        editForm = new VBox(10);
        editForm.setAlignment(Pos.CENTER_RIGHT);
        editForm.setPadding(new Insets(10));
        editForm.setStyle("-fx-border-color: #cccccc; -fx-border-radius: 5; -fx-padding: 10;");

        // فیلدهای ویرایش با اعمال اعتبارسنجی
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

        // کامبوباکس‌ها
        categoryComboBox = new ComboBox<>();
        categoryComboBox.setPromptText("دسته‌بندی را انتخاب کنید");

        supplierComboBox = new ComboBox<>();
        supplierComboBox.setPromptText("تامین کننده را انتخاب کنید");

        brandComboBox = new ComboBox<>();
        brandComboBox.setPromptText("برند را انتخاب کنید");

        quantityField = new TextField();
        quantityField.setPromptText("تعداد");
        quantityField.setTextFormatter(ProductValidation.createQuantityFormatter());

        // بارگذاری داده‌های کامبوباکس‌ها
        loadComboBoxData();

        // دکمه‌ها
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);

        Button saveButton = new Button("ذخیره تغییرات");
        saveButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");

        Button cancelButton = new Button("انصراف");
        cancelButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");

        buttonBox.getChildren().addAll(cancelButton, saveButton);

        saveButton.setOnAction(e -> {
            if (validateFields()) {
                updateProduct();
            }
        });

        cancelButton.setOnAction(e -> {
            showProductTable();
        });

        // اضافه کردن فیلدها به فرم
        editForm.getChildren().addAll(
                createLabeledField("نام محصول:", nameField),
                createLabeledField("قیمت:", priceField),
                createLabeledField("توضیحات:", descriptionArea),
                createLabeledField("دسته‌بندی:", categoryComboBox),
                createLabeledField("تامین کننده:", supplierComboBox),
                createLabeledField("برند:", brandComboBox),
                createLabeledField("تعداد:", quantityField),
                buttonBox
        );
    }

    private void loadTableData() {
        System.out.println("Loading products into table...");
        List<Product> products = ReadAllproduct.Readproduct();
        System.out.println("Found " + products.size() + " products");
        productTable.setItems(FXCollections.observableArrayList(products));
    }

    private void loadProductToForm(Product product) {
        nameField.setText(product.getTitle());
        priceField.setText(String.valueOf(product.getPrice()));
        descriptionArea.setText(product.getDescription());
        categoryComboBox.setValue(product.getCategoryTitle());
        supplierComboBox.setValue(product.getProviderTitle());
        brandComboBox.setValue(product.getBrandTitle());
        quantityField.setText(String.valueOf(product.getQuantity()));
    }

    private void loadComboBoxData() {
        List<String> categories = GetAllCategory.category();
        categoryComboBox.setItems(FXCollections.observableArrayList(categories));

        List<String> providers = GetAllProvider.provider();
        supplierComboBox.setItems(FXCollections.observableArrayList(providers));

        List<String> brands = GetAllBrand.brand();
        brandComboBox.setItems(FXCollections.observableArrayList(brands));
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

    private void updateProduct() {
        Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
        if (selectedProduct == null) {
            showErrorMessage("لطفاً یک محصول را از جدول انتخاب کنید");
            return;
        }

        String originalName = selectedProduct.getTitle();
        String newName = nameField.getText();
        long price = Long.parseLong(priceField.getText());
        String description = descriptionArea.getText();
        String category = categoryComboBox.getValue();
        String supplier = supplierComboBox.getValue();
        String brand = brandComboBox.getValue();
        int quantity = Integer.parseInt(quantityField.getText());

        if (UpdateProduct.updateProduct(originalName, newName, price, description,
                category, supplier, brand, quantity)) {
            showSuccessMessage("محصول با موفقیت به‌روزرسانی شد");
            showProductTable();
        } else {
            showErrorMessage("خطا در به‌روزرسانی محصول");
        }
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