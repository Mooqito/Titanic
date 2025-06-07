package view.Brand;

import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class BrandManagement extends VBox {
    private VBox contentArea;
    private ListView<String> brandListView;

    AddDeleteBrand addDeletebrand = new AddDeleteBrand();
    ShowBrandList showBrandList = new ShowBrandList();

    public BrandManagement(VBox contentArea) {
        super(5);
        this.contentArea = contentArea;
        setVisible(false);
        setManaged(false);
        setStyle("-fx-padding: 0 20 0 0;"); // ایجاد تورفتگی
        setAlignment(Pos.TOP_RIGHT);
        createBrandMenu();
    }

    private void createBrandMenu() {
        String buttonStyle = "-fx-alignment: CENTER_RIGHT; -fx-padding: 5 10 5 5; -fx-content-display: RIGHT;";

        Button addBtn = new Button("افزودن و حذف برند");
        Button showBtn = new Button("نمایش برند‌ها");

        addBtn.setStyle(buttonStyle);
        showBtn.setStyle(buttonStyle);

        addBtn.setMaxWidth(Double.MAX_VALUE);
        showBtn.setMaxWidth(Double.MAX_VALUE);

        addBtn.setOnAction(e -> openBrandWindow("افزودن و حذف برند"));
        showBtn.setOnAction(e -> openBrandWindow("نمایش برند‌ها"));

        getChildren().addAll(addBtn, showBtn);
    }

    private void openBrandWindow(String title) {
        Stage stage = new Stage();
        stage.setTitle(title);

        VBox content = new VBox(10);
        content.setAlignment(Pos.CENTER_RIGHT);
        content.setPadding(new Insets(20));
        content.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);

        if (title.equals("افزودن و حذف برند")) {
            content.getChildren().add(addDeletebrand.createAddDeleteBrandForm());
        } else if (title.equals("نمایش برند‌ها")) {
            content.getChildren().add(showBrandList.createShowBrandsForm());
        }

        Button backBtn = new Button("بازگشت");
        backBtn.setStyle("-fx-alignment: CENTER_RIGHT; -fx-padding: 5 10 5 5;");
        backBtn.setOnAction(e -> stage.close());
        content.getChildren().add(backBtn);

        Scene scene = new Scene(content, 600, 400);
        stage.setScene(scene);
        stage.show();
    }
}