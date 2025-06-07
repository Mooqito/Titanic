package view.Category;

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


public class CategoryManagement extends VBox{

    private VBox contentArea;
    private ListView<String> categoryListView;
    AddDeleteCategory addDeleteCategory = new AddDeleteCategory();
    ShowCategoryList showCategoryList = new ShowCategoryList();

    public CategoryManagement (VBox contentArea){
        super(5);
        this.contentArea = contentArea;
        setVisible(false);
        setManaged(false);
        setStyle("-fx-padding:0 20 0 0;");
        setAlignment(Pos.TOP_RIGHT);
        createCategoryMenu();
    }

    private void createCategoryMenu() {

        String buttnStyle = "-fx-alignment:CENTER_RIGHT;    -fx-padding:5 10 5 5;   -fx-content-display: RIGHT;";
        Button addBut = new Button("افزودن و حذف دسته بندی");
        Button showBut = new Button("نمایش دسته بندی");

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
            content.getChildren().add(addDeleteCategory.createAddDeleteCategoryForm());
        } else if (title.equals("نمایش دسته بندی")) {
            content.getChildren().add(showCategoryList.createShowCategoryForm());
        }

        Button backBut = new Button("بازگشت");
        backBut.setStyle("-fx-alignment: CENTER_RIGHT;-fx-padding: 5 10 5 5");
        backBut.setOnAction(e -> stage.close());
        content.getChildren().add(backBut);

        Scene scene = new Scene(content,600,400);
        stage.setScene(scene);
        stage.show();
    }
}