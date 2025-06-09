package view.product;

import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
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
    private Label statusMessageLabel;

    public EditProductForm() {
        createContent();
    }

    private void createContent() {
        content = new VBox(15);
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(20));
        content.setMaxWidth(Double.MAX_VALUE);
        VBox.setVgrow(content, Priority.ALWAYS);

        titleLabel = new Label("لیست محصولات");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: white;");
        titleLabel.setNodeOrientation(javafx.geometry.NodeOrientation.RIGHT_TO_LEFT);

        HBox titleBox = new HBox();
        titleBox.setAlignment(javafx.geometry.Pos.CENTER_RIGHT);
        titleBox.getChildren().add(titleLabel);

        createProductTable();
        createEditForm();

        // Initially hide the edit form
        editForm.setVisible(false);
        editForm.setManaged(false);

        content.getChildren().addAll(titleBox, productTable, editForm);
    }

    private void createProductTable() {
        productTable = new TableView<>();
        productTable.setMaxWidth(950);
        productTable.setPrefHeight(400);
        productTable.setStyle("-fx-alignment: CENTER-RIGHT;");
        productTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Style for column headers similar to management menu buttons
        productTable.getStylesheets().add("data:text/css," +
                ".table-view .column-header-background {" +
                "    -fx-background-color: linear-gradient(to bottom, rgb(200, 215, 230), rgb(180, 195, 210));" + // گرادیان آبی کم رنگ مایل به طوسی
                "    -fx-background-radius: 5px 5px 0 0;" +
                "    -fx-border-color: rgb(160, 180, 200);" +
                "    -fx-border-width: 1px 1px 0 1px;" +
                "    -fx-border-radius: 5px 5px 0 0;" +
                "}" +
                ".table-view .column-header {" +
                "    -fx-background-color: transparent;" +
                "    -fx-border-color: rgb(160, 180, 200);" +
                "    -fx-border-width: 0 1px 0 0;" +
                "}" +
                ".table-view .filler {" +
                "    -fx-background-color: transparent;" +
                "}" +
                ".table-view .column-header, .table-view .filler { -fx-size: 50px; }" +
                ".table-view .column-header .label {" +
                "    -fx-text-fill: white;" +
                "    -fx-font-weight: bold;" +
                "}"
        );

        // Define columns
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

        // Set right-to-left alignment for all columns
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

        // Set row height
        productTable.setRowFactory(tv -> {
            TableRow<Product> row = new TableRow<>();
            row.setPrefHeight(60);
            return row;
        });

        // Load data
        loadTableData();

        // Product selection event
        productTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                System.out.println("Selected product: " + newSelection.getTitle());
                loadProductToForm(newSelection);
                showEditForm();
            }
        });

        productTable.setPrefHeight(500);

        // Set placeholder text when table is empty
        Label noContentLabel = new Label(" محصولی برای نمایش وجود ندارد ");
        noContentLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: gray;");
        productTable.setPlaceholder(noContentLabel);
    }

    private void showEditForm() {
        // Hide table and change title
        titleLabel.setText("ویرایش محصول");
        productTable.setVisible(false);
        productTable.setManaged(false);

        // Show edit form
        editForm.setVisible(true);
        editForm.setManaged(true);
    }

    private void showProductTable() {
        // Show table again and change title
        titleLabel.setText("لیست محصولات");
        productTable.setVisible(true);
        productTable.setManaged(true);

        // Hide edit form
        editForm.setVisible(false);
        editForm.setManaged(false);

        // Clear selection in table
        productTable.getSelectionModel().clearSelection();

        loadTableData();
        // Clear any previous status message
        statusMessageLabel.setText("");
    }

    private void createEditForm() {
        editForm = new VBox(15);
        editForm.setAlignment(Pos.CENTER);
        editForm.setStyle("-fx-background-color: #3a5c79; -fx-padding: 20;");
        editForm.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
        editForm.setPrefWidth(800);
        editForm.setMaxWidth(800);

        // Initialize status message label for edit form
        statusMessageLabel = new Label("");
        statusMessageLabel.setStyle("-fx-font-size: 14px; -fx-padding: 5 0;");

        // Edit fields with validation
        nameField = new TextField();
        nameField.setPromptText("نام محصول");
        nameField.setPrefHeight(40);
        nameField.setStyle("-fx-background-color: rgba(255, 255, 255, 0.58); -fx-text-fill: black; -fx-alignment: CENTER-RIGHT; -fx-text-alignment: RIGHT;");
        nameField.setTextFormatter(ProductValidation.createTitleFormatter());
        nameField.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);

        priceField = new TextField();
        priceField.setPromptText("قیمت");
        priceField.setPrefHeight(40);
        priceField.setStyle("-fx-background-color: rgba(255, 255, 255, 0.58); -fx-text-fill: black; -fx-alignment: CENTER-RIGHT; -fx-text-alignment: RIGHT;");
        priceField.setTextFormatter(ProductValidation.createPriceFormatter());
        priceField.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);

        descriptionArea = new TextArea();
        descriptionArea.setPromptText("توضیحات");
        descriptionArea.setPrefRowCount(4);
        descriptionArea.setPrefHeight(120);
        descriptionArea.setStyle("-fx-background-color: rgba(255, 255, 255, 0.58); -fx-text-fill: black; -fx-alignment: CENTER-RIGHT; -fx-text-alignment: RIGHT;");
        descriptionArea.setTextFormatter(ProductValidation.createDescriptionFormatter());
        descriptionArea.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);

        // Combo boxes
        categoryComboBox = new ComboBox<>();
        categoryComboBox.setPromptText("دسته‌بندی را انتخاب کنید");
        categoryComboBox.setPrefHeight(40);
        categoryComboBox.setPrefWidth(400);
        categoryComboBox.setStyle("-fx-background-color: rgba(255, 255, 255, 0.58); -fx-text-fill: white; -fx-alignment: CENTER-RIGHT; -fx-text-alignment: RIGHT;");
        categoryComboBox.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);

        supplierComboBox = new ComboBox<>();
        supplierComboBox.setPromptText("تامین کننده را انتخاب کنید");
        supplierComboBox.setPrefHeight(40);
        supplierComboBox.setPrefWidth(400);
        supplierComboBox.setStyle("-fx-background-color: rgba(255, 255, 255, 0.58); -fx-text-fill: white; -fx-alignment: CENTER-RIGHT; -fx-text-alignment: RIGHT;");
        supplierComboBox.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);

        brandComboBox = new ComboBox<>();
        brandComboBox.setPromptText("برند را انتخاب کنید");
        brandComboBox.setPrefHeight(40);
        brandComboBox.setPrefWidth(400);
        brandComboBox.setStyle("-fx-background-color: rgba(255, 255, 255, 0.58); -fx-text-fill: white; -fx-alignment: CENTER-RIGHT; -fx-text-alignment: RIGHT;");
        brandComboBox.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);

        quantityField = new TextField();
        quantityField.setPromptText("تعداد");
        quantityField.setPrefHeight(40);
        quantityField.setStyle("-fx-background-color: rgba(255, 255, 255, 0.58); -fx-text-fill: black; -fx-alignment: CENTER-RIGHT; -fx-text-alignment: RIGHT;");
        quantityField.setTextFormatter(ProductValidation.createQuantityFormatter());
        quantityField.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);

        // Load combo box data
        loadComboBoxData();

        // Buttons
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

        // Add fields to form
        editForm.getChildren().addAll(
                createLabeledField("نام محصول:", nameField),
                createLabeledField("قیمت:", priceField),
                createLabeledField("توضیحات:", descriptionArea),
                createLabeledField("دسته‌بندی:", categoryComboBox),
                createLabeledField("تامین کننده:", supplierComboBox),
                createLabeledField("برند:", brandComboBox),
                createLabeledField("تعداد:", quantityField),
                buttonBox,
                statusMessageLabel
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
        box.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(box, Priority.ALWAYS);

        Label label = new Label(labelText);
        label.setMinWidth(100);
        label.setAlignment(Pos.CENTER_RIGHT);
        label.setStyle("-fx-text-fill: white;");

        // Ensure the field itself can grow horizontally
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
        statusMessageLabel.setText(message);
        statusMessageLabel.setStyle("-fx-text-fill: #00ba2d; -fx-font-size: 14px; -fx-padding: 5 0;");
        hideMessageAfterDelay();
    }

    private void showErrorMessage(String message) {
        statusMessageLabel.setText(message);
        statusMessageLabel.setStyle("-fx-text-fill: #7a0000; -fx-font-size: 14px; -fx-padding: 5 0;");
        hideMessageAfterDelay();
    }

    private void hideMessageAfterDelay() {
        javafx.animation.Timeline timeline = new javafx.animation.Timeline(new javafx.animation.KeyFrame(javafx.util.Duration.seconds(3), event -> {
            statusMessageLabel.setText("");
        }));
        timeline.setCycleCount(1);
        timeline.play();
    }

    public VBox getContent() {
        return content;
    }
}