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
        root.setAlignment(Pos.CENTER_RIGHT);
        root.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);

        Label titleLabel = new Label("لیست کالاها");
        titleLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        TableView<Product> table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

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

        table.setRowFactory(tv -> {
            TableRow<Product> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty()) {
                    Product clickedProduct = row.getItem();
                    showProductDetail(clickedProduct);
                }
            });
            return row;
        });

        root.getChildren().addAll(titleLabel, table);
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
