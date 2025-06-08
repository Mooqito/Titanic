package view.product;

import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.collections.FXCollections;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Product.product.Product;
import model.Product.product.DeleteProduct;
import model.Product.product.ReadAllproduct;

import java.util.List;

public class DeleteProductForm {

    private VBox content;
    private TableView<Product> productTable;
    private Button deleteButton;
    private Label titleLabel;
    private Label statusMessageLabel;

    public DeleteProductForm() {
        createContent();
    }

    private void createContent() {
        content = new VBox(15);
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(20));
        content.setStyle("-fx-background-color: #3a5c79; -fx-padding: 20;");
        content.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);

        titleLabel = new Label("حذف کالا");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: white;");

        HBox titleBox = new HBox();
        titleBox.setAlignment(Pos.CENTER_RIGHT);
        titleBox.getChildren().add(titleLabel);

        createProductTable();

        statusMessageLabel = new Label();
        statusMessageLabel.setStyle("-fx-font-size: 14px; -fx-padding: 5 0;");

        deleteButton = new Button("حذف کالا");
        deleteButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");
        deleteButton.setDisable(true);

        HBox deleteButtonBox = new HBox();
        deleteButtonBox.setAlignment(Pos.CENTER_LEFT);
        deleteButtonBox.getChildren().addAll(deleteButton, statusMessageLabel);

        deleteButton.setOnAction(e -> {
            Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
            if (selectedProduct != null) {
                boolean deleted = DeleteProduct.deleteProduct(selectedProduct.getTitle());
                if (deleted) {
                    showSuccessMessage("کالا با موفقیت حذف شد.");
                    loadTableData();
                    deleteButton.setDisable(true);
                } else {
                    showErrorMessage("خطا در حذف کالا.");
                }
            }
        });

        // Enable/disable delete button based on table selection
        productTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            deleteButton.setDisable(newSelection == null);
        });

        content.getChildren().addAll(titleBox, productTable, deleteButtonBox);
    }

    private void createProductTable() {
        productTable = new TableView<>();
        productTable.setMaxWidth(950);
        productTable.setPrefHeight(400);
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

        // Right-align columns
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

        productTable.setPrefHeight(500);

        // Set placeholder text when table is empty
        Label noContentLabel = new Label("محصولی برای نمایش وجود ندارد");
        noContentLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: gray;");
        productTable.setPlaceholder(noContentLabel);

        loadTableData();
    }

    private void loadTableData() {
        List<Product> products = ReadAllproduct.Readproduct();
        productTable.setItems(FXCollections.observableArrayList(products));
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