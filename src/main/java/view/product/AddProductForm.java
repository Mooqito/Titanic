package view.product;

import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.collections.FXCollections;
import model.Product.brand.BrandInputToDB;
import model.Product.brand.GetAllBrand;
import model.Product.category.CategoruInputToDB;
import model.Product.category.GetAllCategory;
import model.Product.product.ProductInputToDB;
import model.Product.provider.GetAllProvider;
import model.Product.provider.ProviderInputToDB;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

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
    private Label messageLabel;

    public AddProductForm() {
        createContent();
    }

    private void createContent() {
        content = new VBox(15);
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(20));
        content.setStyle("-fx-background-color: #3a5c79; -fx-padding: 20;");
        content.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);

        nameField = new TextField();
        nameField.setPromptText("نام محصول");
        nameField.setTextFormatter(ProductValidation.createTitleFormatter());
        nameField.setPrefHeight(35);
        nameField.setStyle("-fx-control-inner-background: rgba(175, 180, 204, 0.58); -fx-text-fill: white; -fx-prompt-text-fill: white; -fx-font-size: 14px;");
        nameField.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);

        priceField = new TextField();
        priceField.setPromptText("قیمت");
        priceField.setTextFormatter(ProductValidation.createPriceFormatter());
        priceField.setPrefHeight(35);
        priceField.setStyle("-fx-control-inner-background: rgba(175, 180, 204, 0.58); -fx-text-fill: white; -fx-prompt-text-fill: white; -fx-font-size: 14px;");
        priceField.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);

        descriptionArea = new TextArea();
        descriptionArea.setPromptText("توضیحات");
        descriptionArea.setPrefRowCount(3);
        descriptionArea.setTextFormatter(ProductValidation.createDescriptionFormatter());
        descriptionArea.setPrefHeight(80);
        descriptionArea.setStyle("-fx-control-inner-background: rgba(175, 180, 204, 0.58); -fx-text-fill: black; -fx-prompt-text-fill: black;");
        descriptionArea.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);

        HBox categoryBox = createComboWithAddButton("دسته‌بندی", categoryComboBox = new ComboBox<>());
        HBox supplierBox = createComboWithAddButton("تامین کننده", supplierComboBox = new ComboBox<>());
        HBox brandBox = createComboWithAddButton("برند", brandComboBox = new ComboBox<>());

        // Set initial style for ComboBoxes (to ensure prompt text is white)
        categoryComboBox.setStyle("-fx-control-inner-background: rgba(175, 180, 204, 0.58); -fx-text-fill: white; -fx-prompt-text-fill: white; -fx-font-size: 14px;");
        categoryComboBox.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        supplierComboBox.setStyle("-fx-control-inner-background: rgba(175, 180, 204, 0.58); -fx-text-fill: white; -fx-prompt-text-fill: white; -fx-font-size: 14px;");
        supplierComboBox.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        brandComboBox.setStyle("-fx-control-inner-background: rgba(175, 180, 204, 0.58); -fx-text-fill: white; -fx-prompt-text-fill: white; -fx-font-size: 14px;");
        brandComboBox.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);

        quantityField = new TextField();
        quantityField.setPromptText("تعداد");
        quantityField.setTextFormatter(ProductValidation.createQuantityFormatter());
        quantityField.setPrefHeight(35);
        quantityField.setStyle("-fx-control-inner-background: rgba(175, 180, 204, 0.58); -fx-text-fill: white; -fx-prompt-text-fill: white; -fx-font-size: 14px;");
        quantityField.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);

        loadComboBoxData();

        Button submitButton = new Button("ثبت محصول");
        submitButton.setMaxWidth(200);
        submitButton.setStyle("-fx-background-color:rgb(2, 75, 2); -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10 20; -fx-background-radius: 5;");
        submitButton.setPrefHeight(35);

        messageLabel = new Label();
        messageLabel.setStyle("-fx-font-size: 14px; -fx-padding: 0 10;");

        HBox submitButtonBox = new HBox(10);
        submitButtonBox.setAlignment(Pos.CENTER_LEFT);
        submitButtonBox.getChildren().addAll(submitButton, messageLabel);

        submitButton.setOnAction(e -> {
            if (validateFields()) {
                String name = nameField.getText();
                long price = Long.parseLong(priceField.getText());
                String description = descriptionArea.getText();
                String category = categoryComboBox.getValue();
                String supplier = supplierComboBox.getValue();
                String brand = brandComboBox.getValue();
                int quantity = Integer.parseInt(quantityField.getText());

                if (ProductInputToDB.productExist(name, price, category, supplier, brand)) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("محصول موجود");
                    alert.setHeaderText(null);
                    alert.setContentText("محصول با این مشخصات وجود دارد. آیا می‌خواهید تعداد آن را افزایش دهید؟");
                    
                    // Set RTL orientation
                    alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
                    
                    // Style the dialog pane
                    alert.getDialogPane().setStyle("-fx-background-color: rgba(175, 180, 204, 0.58);");
                    
                    // Style the content text
                    Label contentLabel = (Label) alert.getDialogPane().lookup(".content.label");
                    if (contentLabel != null) {
                        contentLabel.setStyle("-fx-text-fill: white; -fx-font-size: 14px;");
                    }
                    
                    // Style the buttons
                    ButtonBar buttonBar = (ButtonBar) alert.getDialogPane().lookup(".button-bar");
                    if (buttonBar != null) {
                        buttonBar.setStyle("-fx-background-color: transparent;");
                        buttonBar.setButtonOrder(ButtonBar.BUTTON_ORDER_NONE);
                        buttonBar.setStyle("-fx-background-color: transparent; -fx-alignment: CENTER_LEFT;");
                    }

                    alert.showAndWait().ifPresent(response -> {
                        if (response == ButtonType.OK) {
                            boolean success = ProductInputToDB.updateProductQuantity(name, price, category, supplier, brand, quantity);
                            if (success) {
                                showSuccessMessage("تعداد محصول با موفقیت به‌روزرسانی شد");
                                clearFields();
                            } else {
                                showErrorMessage("خطا در به‌روزرسانی تعداد");
                            }
                        }
                    });
                } else {
                    boolean success = ProductInputToDB.productInput(name, price, description, category, supplier, brand, quantity);
                    if (success) {
                        showSuccessMessage("محصول با موفقیت ثبت شد");
                        clearFields();
                    } else {
                        showErrorMessage("خطا در ثبت محصول. احتمالاً عنوان محصول تکراری است.");
                    }
                }
            }
        });

        content.getChildren().addAll(
                createLabeledField("نام محصول:", nameField),
                createLabeledField("قیمت:", priceField),
                createLabeledField("توضیحات:", descriptionArea),
                categoryBox,
                supplierBox,
                brandBox,
                createLabeledField("تعداد:", quantityField),
                submitButtonBox
        );
    }

    private HBox createLabeledField(String labelText, Control field) {
        HBox box = new HBox(10);
        box.setAlignment(Pos.CENTER);

        Label label = new Label(labelText);
        label.setMinWidth(100);
        label.setAlignment(Pos.CENTER_RIGHT);
        label.setStyle("-fx-text-fill: white;");

        field.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(field, Priority.ALWAYS);
        if (field instanceof TextField || field instanceof TextArea || field instanceof ComboBox) {
            field.setStyle("-fx-control-inner-background: rgba(120, 125, 145, 0.8); -fx-text-fill: white; -fx-prompt-text-fill: white; -fx-font-size: 14px;");
        }

        box.getChildren().addAll(field, label);
        return box;
    }

    private HBox createComboWithAddButton(String labelText, ComboBox<String> comboBox) {
        HBox box = new HBox(10);
        box.setAlignment(Pos.CENTER);

        Button addButton = new Button("+");
        addButton.setOnAction(e -> showAddDialog(labelText, comboBox));
        addButton.setStyle("-fx-background-color: rgba(120, 125, 145, 0.8); -fx-text-fill: white; -fx-font-size: 14px; -fx-background-radius: 5;");

        Label label = new Label(labelText + ":");
        label.setMinWidth(100);
        label.setAlignment(Pos.CENTER_RIGHT);
        label.setStyle("-fx-text-fill: white;");

        comboBox.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(comboBox, Priority.ALWAYS);
        comboBox.setStyle("-fx-control-inner-background: rgba(120, 125, 145, 0.8); -fx-text-fill: white; -fx-prompt-text-fill: white; -fx-font-size: 14px;");
        comboBox.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);

        box.getChildren().addAll(addButton, comboBox, label);
        return box;
    }

    private void showAddDialog(String type, ComboBox<String> comboBox) {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("افزودن " + type);
        dialog.setHeaderText(null);

        // Set dialog background color
        dialog.getDialogPane().setStyle("-fx-background-color: rgba(175, 180, 204, 0.58);");

        ButtonType addButtonType = new ButtonType("افزودن", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButtonType = new ButtonType("لغو", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(cancelButtonType, addButtonType);

        // Set button order to none and manually arrange
        ButtonBar buttonBar = (ButtonBar) dialog.getDialogPane().lookup(".button-bar");
        buttonBar.setButtonOrder(ButtonBar.BUTTON_ORDER_NONE);
        buttonBar.setStyle("-fx-alignment: CENTER_LEFT;");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField nameField = new TextField();
        nameField.setPromptText("نام " + type);
        // Right-align prompt text and input text
        nameField.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        nameField.setStyle("-fx-control-inner-background: rgba(175, 180, 204, 0.58); -fx-text-fill: white; -fx-prompt-text-fill: white; -fx-font-size: 14px;");

        Label nameLabel = new Label("نام " + type + ":");
        nameLabel.setStyle("-fx-text-fill: black;");

        // Add label to the right of the text field
        grid.add(nameField, 0, 0);
        grid.add(nameLabel, 1, 0);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButtonType) {
                return nameField.getText();
            }
            return null;
        });

        dialog.showAndWait().ifPresent(result -> {
            if (!result.isEmpty()) {
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
        messageLabel.setText(message);
        messageLabel.setStyle("-fx-text-fill: #00ba2d; -fx-font-size: 14px; -fx-padding: 0 10;");
        hideMessageAfterDelay();
    }

    private void showErrorMessage(String message) {
        messageLabel.setText(message);
        messageLabel.setStyle("-fx-text-fill: #7a0000; -fx-font-size: 14px; -fx-padding: 0 10;");
        hideMessageAfterDelay();
    }

    private void hideMessageAfterDelay() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(4), event -> {
            messageLabel.setText("");
        }));
        timeline.setCycleCount(1);
        timeline.play();
    }

    public VBox getContent() {
        return content;
    }
}