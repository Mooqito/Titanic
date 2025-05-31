package view;

import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class ProductManagement extends VBox {
    private VBox contentArea;

    public ProductManagement(VBox contentArea) {
        super(5);
        this.contentArea = contentArea;
        setVisible(false);
        setManaged(false);
        setStyle("-fx-padding: 0 20 0 0;"); // ایجاد تورفتگی
        setAlignment(Pos.TOP_RIGHT);
        createProductMenu();
    }

    private void createProductMenu() {
        String buttonStyle = "-fx-alignment: CENTER_RIGHT; -fx-padding: 5 10 5 5; -fx-content-display: RIGHT;";

        Button addBtn = new Button("افزودن کالا");
        Button editBtn = new Button("ویرایش کالا");
        Button deleteBtn = new Button("حذف کالا");
        Button showBtn = new Button("نمایش کالا");

        addBtn.setStyle(buttonStyle);
        editBtn.setStyle(buttonStyle);
        deleteBtn.setStyle(buttonStyle);
        showBtn.setStyle(buttonStyle);

        addBtn.setMaxWidth(Double.MAX_VALUE);
        editBtn.setMaxWidth(Double.MAX_VALUE);
        deleteBtn.setMaxWidth(Double.MAX_VALUE);
        showBtn.setMaxWidth(Double.MAX_VALUE);

        addBtn.setOnAction(e -> openProductWindow("افزودن کالا"));
        editBtn.setOnAction(e -> openProductWindow("ویرایش کالا"));
        deleteBtn.setOnAction(e -> openProductWindow("حذف کالا"));
        showBtn.setOnAction(e -> openProductWindow("نمایش کالا"));

        getChildren().addAll(addBtn, editBtn, deleteBtn, showBtn);
    }

    private void openProductWindow(String title) {
        Stage stage = new Stage();
        stage.setTitle(title);

        VBox content = new VBox(10);
        content.setAlignment(Pos.CENTER_RIGHT);
        content.setPadding(new Insets(20));
        content.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);

        switch (title) {
            case "افزودن کالا":
                content.getChildren().add(new AddProductForm().getContent());
                break;
            case "ویرایش کالا":
                content.getChildren().add(createEditForm());
                break;
            case "حذف کالا":
                content.getChildren().add(createDeleteForm());
                break;
            case "نمایش کالا":
                content.getChildren().add(createShowForm());
                break;
        }

        Button backBtn = new Button("بازگشت");
        backBtn.setStyle("-fx-alignment: CENTER_RIGHT; -fx-padding: 5 10 5 5;");
        backBtn.setOnAction(e -> stage.close());
        content.getChildren().add(backBtn);

        Scene scene = new Scene(content, 600, 400);
        stage.setScene(scene);
        stage.show();
    }

    private VBox createEditForm() {
        VBox form = new VBox(10);
        form.setAlignment(Pos.CENTER_RIGHT);

        // TODO: پیاده‌سازی فرم ویرایش محصول
        Label label = new Label("فرم ویرایش محصول در حال توسعه...");
        label.setStyle("-fx-font-size: 14px;");
        form.getChildren().add(label);

        return form;
    }

    private VBox createDeleteForm() {
        VBox form = new VBox(10);
        form.setAlignment(Pos.CENTER_RIGHT);

        // TODO: پیاده‌سازی فرم حذف محصول
        Label label = new Label("فرم حذف محصول در حال توسعه...");
        label.setStyle("-fx-font-size: 14px;");
        form.getChildren().add(label);

        return form;
    }

    private VBox createShowForm() {
        VBox form = new VBox(10);
        form.setAlignment(Pos.CENTER_RIGHT);

        // TODO: پیاده‌سازی فرم نمایش محصول
        Label label = new Label("فرم نمایش محصول در حال توسعه...");
        label.setStyle("-fx-font-size: 14px;");
        form.getChildren().add(label);

        return form;
    }
}