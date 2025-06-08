package view.Category;

import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Product.brand.DeleteBrand;
import model.Product.category.CategoruInputToDB;
import model.Product.category.Category;
import model.Product.category.GetAllCategory;
import model.Product.product.Product;
import model.Product.product.ReadAllproduct;
import runner.Main;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// Category Management class for handling category-related operations
public class CategoryManagement extends VBox {

    private VBox contentArea;
    private ListView<String> categoryListView;
    AddDeleteCategory addDeleteCategory = new AddDeleteCategory();
    ShowCategoryList showCategoryList = new ShowCategoryList();

    public CategoryManagement(VBox contentArea) {
        super(5);
        this.contentArea = contentArea;
        setVisible(false);
        setManaged(false);
        setStyle("-fx-padding:0 35 0 0;");
        setAlignment(Pos.TOP_RIGHT);
        createCategoryMenu();
    }

    private void createCategoryMenu() {
        String buttonStyle = "-fx-background-color: rgba(175, 180, 204, 0.58); -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10 20; -fx-background-radius: 5; -fx-alignment: CENTER_RIGHT; -fx-content-display: RIGHT;";
        String buttonHoverStyle = "-fx-background-color: rgba(175, 180, 204, 0.58); -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10 20; -fx-background-radius: 5; -fx-effect: dropshadow(gaussian, #a6cbeb, 10, 0.5, 0, 0); -fx-alignment: CENTER_RIGHT; -fx-content-display: RIGHT;";

        Button addBtn = createCategoryButton("افزودن و حذف دسته بندی", "/images/AddDeleteBrand.png", buttonStyle, buttonHoverStyle);
        Button showBtn = createCategoryButton("نمایش دسته بندی", "/images/ShowProduct.png", buttonStyle, buttonHoverStyle);

        addBtn.setPrefWidth(240);
        showBtn.setPrefWidth(240);

        addBtn.setOnAction(e -> {
            contentArea.getChildren().clear();
            contentArea.getChildren().add(addDeleteCategory.createAddDeleteCategoryForm());
        });
        showBtn.setOnAction(e -> {
            contentArea.getChildren().clear();
            contentArea.getChildren().add(showCategoryList.createShowCategoryForm());
        });

        getChildren().addAll(addBtn, showBtn);
    }

    private Button createCategoryButton(String text, String imagePath, String buttonStyle, String buttonHoverStyle) {
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