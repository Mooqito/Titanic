package view.Brand;

import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class BrandManagement extends VBox {
    private VBox contentArea;
    private ListView<String> brandListView;

    private AddDeleteBrand addDeletebrand;
    private ShowBrandList showBrandList;

    public BrandManagement(VBox contentArea) {
        super(5);
        this.contentArea = contentArea;
        setVisible(false);
        setManaged(false);
        setStyle("-fx-padding: 0 35 0 0;"); // Create indentation
        setAlignment(Pos.TOP_RIGHT);

        // Set right-to-left orientation for contentArea
        // Remove this line to prevent affecting other forms
        // this.contentArea.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        this.contentArea.setAlignment(Pos.TOP_RIGHT);

        // Initialize the components with required parameters
        this.addDeletebrand = new AddDeleteBrand(contentArea, this);
        this.showBrandList = new ShowBrandList(contentArea, this);

        createBrandMenu();
    }

    private void createBrandMenu() {
        String buttonStyle = "-fx-background-color: rgba(175, 180, 204, 0.58); -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10 20; -fx-background-radius: 5; -fx-alignment: CENTER_RIGHT; -fx-content-display: RIGHT;";
        String buttonHoverStyle = "-fx-background-color: rgba(175, 180, 204, 0.58); -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10 20; -fx-background-radius: 5; -fx-effect: dropshadow(gaussian, #a6cbeb, 10, 0.5, 0, 0); -fx-alignment: CENTER_RIGHT; -fx-content-display: RIGHT;";

        Button addBtn = createBrandButton("افزودن و حذف برند", "/images/AddDeleteBrand.png", buttonStyle, buttonHoverStyle);
        Button showBtn = createBrandButton("نمایش برند‌ها", "/images/ShowProduct.png", buttonStyle, buttonHoverStyle);

        addBtn.setPrefWidth(240);
        showBtn.setPrefWidth(240);

        addBtn.setOnAction(e -> {
            contentArea.getChildren().clear();
            contentArea.getChildren().add(addDeletebrand.createAddDeleteBrandForm());
        });
        showBtn.setOnAction(e -> {
            contentArea.getChildren().clear();
            contentArea.getChildren().add(showBrandList.createShowBrandsForm());
        });

        getChildren().addAll(addBtn, showBtn);
    }

    private Button createBrandButton(String text, String imagePath, String buttonStyle, String buttonHoverStyle) {
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
}