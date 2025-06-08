package view.product;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.Product.product.ReadAllproduct;
import model.Product.product.Product;

import java.util.List;

public class ShowProductForm {

    public VBox getContent() {
        VBox root = new VBox(15);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.TOP_CENTER);
        root.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);

        Label titleLabel = new Label("لیست کالاها");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: white;");

        // Container for title label (right-aligned)
        HBox titleBox = new HBox();
        titleBox.setAlignment(Pos.CENTER_LEFT);
        titleBox.getChildren().add(titleLabel);

        TableView<Product> table = new TableView<>();
        table.setMaxWidth(950);
        table.setPrefHeight(400);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Style for column headers similar to management menu buttons
        table.getStylesheets().add("data:text/css," +
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

        TableColumn<Product, Long> idCol = new TableColumn<>("شناسه");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Product, String> titleCol = new TableColumn<>("عنوان");
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<Product, Long> priceCol = new TableColumn<>("قیمت");
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<Product, Long> quantityCol = new TableColumn<>("موجودی");
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        TableColumn<Product, String> categoryCol = new TableColumn<>("دسته‌بندی");
        categoryCol.setCellValueFactory(new PropertyValueFactory<>("categoryTitle"));

        TableColumn<Product, String> brandCol = new TableColumn<>("برند");
        brandCol.setCellValueFactory(new PropertyValueFactory<>("brandTitle"));

        TableColumn<Product, String> providerCol = new TableColumn<>("تأمین‌کننده");
        providerCol.setCellValueFactory(new PropertyValueFactory<>("providerTitle"));

        table.getColumns().addAll(idCol, titleCol, priceCol, quantityCol, categoryCol, brandCol, providerCol);

        List<Product> productList = ReadAllproduct.Readproduct();
        ObservableList<Product> products = FXCollections.observableArrayList(productList);
        table.setItems(products);

        table.setPrefHeight(500); 

        // Set row height
        table.setRowFactory(tv -> {
            TableRow<Product> row = new TableRow<>();
            row.setPrefHeight(60); 
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty()) {
                    Product clickedProduct = row.getItem();
                    showProductDetail(clickedProduct);
                }
            });
            return row;
        });

        // Set placeholder text when table is empty
        Label noContentLabel = new Label("محصولی برای نمایش وجود ندارد");
        noContentLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: gray;");
        table.setPlaceholder(noContentLabel);

        root.getChildren().addAll(titleBox, table);
        return root;
    }

    private void showProductDetail(Product product) {
        Stage detailStage = new Stage();
        detailStage.setTitle("جزئیات کالا");

        VBox detailBox = new VBox(10);
        detailBox.setPadding(new Insets(20));
        detailBox.setAlignment(Pos.TOP_RIGHT);
        detailBox.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);

        String style = "-fx-font-size: 14px;";

        Label titleLabel = new Label("عنوان: " + product.getTitle());
        Label idLabel = new Label("شناسه: " + product.getId());
        Label priceLabel = new Label("قیمت: " + product.getPrice());
        Label quantityLabel = new Label("موجودی: " + product.getQuantity());
        Label descLabel = new Label("توضیحات: " + product.getDescription());
        Label catLabel = new Label("دسته‌بندی: " + product.getCategoryTitle());
        Label brandLabel = new Label("برند: " + product.getBrandTitle());
        Label provLabel = new Label("تأمین‌کننده: " + product.getProviderTitle());

        titleLabel.setStyle(style);
        idLabel.setStyle(style);
        priceLabel.setStyle(style);
        quantityLabel.setStyle(style);
        descLabel.setStyle(style);
        catLabel.setStyle(style);
        brandLabel.setStyle(style);
        provLabel.setStyle(style);

        Button closeBtn = new Button("بستن");
        closeBtn.setOnAction(e -> detailStage.close());

        detailBox.getChildren().addAll(
                titleLabel, idLabel, priceLabel, quantityLabel,
                descLabel, catLabel, brandLabel, provLabel, closeBtn
        );

        Scene scene = new Scene(detailBox, 400, 350);
        detailStage.setScene(scene);
        detailStage.show();
    }
}