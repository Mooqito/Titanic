package view.product;

import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class ProductManagement extends VBox {
    private VBox contentArea;

    public ProductManagement(VBox contentArea) {
        super(5);
        this.contentArea = contentArea;
        this.contentArea.setAlignment(Pos.CENTER);
        this.contentArea.setMaxWidth(Double.MAX_VALUE);
        this.contentArea.setPadding(new Insets(20));
        VBox.setVgrow(this.contentArea, Priority.ALWAYS);
        this.contentArea.setStyle("-fx-background-color: #3a5c79;");
        this.contentArea.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
        setVisible(false);
        setManaged(false);
        setStyle("-fx-padding: 0 35 0 0;"); // ایجاد تورفتگی
        setAlignment(Pos.TOP_RIGHT);
        createProductMenu();
    }

    private void createProductMenu() {
        String buttonStyle = "-fx-background-color: rgba(175, 180, 204, 0.58); -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10 20; -fx-background-radius: 5; -fx-alignment: CENTER_RIGHT; -fx-content-display: RIGHT;";
        String buttonHoverStyle = "-fx-background-color: rgba(175, 180, 204, 0.58); -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10 20; -fx-background-radius: 5; -fx-effect: dropshadow(gaussian, #a6cbeb, 10, 0.5, 0, 0); -fx-alignment: CENTER_RIGHT; -fx-content-display: RIGHT;";

        Button addBtn = createProductButton("افزودن کالا", "/images/AddProduct.png", buttonStyle, buttonHoverStyle);
        Button editBtn = createProductButton("ویرایش کالا", "/images/EditProduct.png", buttonStyle, buttonHoverStyle);
        Button deleteBtn = createProductButton("حذف کالا", "/images/DeleteProduct.png", buttonStyle, buttonHoverStyle);
        Button showBtn = createProductButton("نمایش کالا", "/images/ShowProduct.png", buttonStyle, buttonHoverStyle);

        addBtn.setPrefWidth(240);
        editBtn.setPrefWidth(240);
        deleteBtn.setPrefWidth(240);
        showBtn.setPrefWidth(240);

        addBtn.setOnAction(e -> openProductWindow("افزودن کالا"));
        editBtn.setOnAction(e -> openProductWindow("ویرایش کالا"));
        deleteBtn.setOnAction(e -> openProductWindow("حذف کالا"));
        showBtn.setOnAction(e -> openProductWindow("نمایش کالا"));

        getChildren().addAll(addBtn, editBtn, deleteBtn, showBtn);
    }

    private Button createProductButton(String text, String imagePath, String buttonStyle, String buttonHoverStyle) {
        ImageView icon = new ImageView(new Image(getClass().getResourceAsStream(imagePath)));
        icon.setFitWidth(35);
        icon.setFitHeight(35);

        Label textLabel = new Label(text);
        textLabel.setFont(javafx.scene.text.Font.font("System", 14));
        textLabel.setTextFill(javafx.scene.paint.Color.WHITE);

        HBox buttonContent = new HBox(10);
        buttonContent.setAlignment(Pos.CENTER_LEFT);
        buttonContent.getChildren().addAll(icon, textLabel);
        buttonContent.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);

        Button button = new Button();
        button.setGraphic(buttonContent);
        button.setStyle(buttonStyle);

        button.setOnMouseEntered(e -> button.setStyle(buttonHoverStyle));
        button.setOnMouseExited(e -> button.setStyle(buttonStyle));

        return button;
    }

    private void openProductWindow(String title) {
        // clear the content area first to remove any previous forms
        contentArea.getChildren().clear();

        VBox formContent = null;

        switch (title) {
            case "افزودن کالا":
                formContent = new AddProductForm().getContent();
                break;
            case "ویرایش کالا":
                formContent = createEditForm();
                break;
            case "حذف کالا":
                formContent = createDeleteForm();
                break;
            case "نمایش کالا":
                formContent = createShowForm();
                break;
        }

        if (formContent != null) {
            contentArea.getChildren().add(formContent);
            // Set alignment and padding for the form within the contentArea
            formContent.setAlignment(Pos.CENTER); // Set alignment to center
            VBox.setVgrow(formContent, Priority.ALWAYS); // Allow form to grow vertically
        }
    }

    private VBox createEditForm() {
        EditProductForm editForm = new EditProductForm();
        return editForm.getContent();
    }

    private VBox createDeleteForm() {
        DeleteProductForm deleteForm = new DeleteProductForm();
        return deleteForm.getContent();
    }

    private VBox createShowForm() {
        ShowProductForm showForm = new ShowProductForm();
        return showForm.getContent();
    }
}