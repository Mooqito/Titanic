package view;

import javafx.geometry.Insets;
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

    public DeleteProductForm() {
        createContent();
    }

    private void createContent() {
        content = new VBox(15);
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(20));
        content.setMaxWidth(900);

        titleLabel = new Label("حذف کالا");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        createProductTable();

        deleteButton = new Button("حذف کالا");
        deleteButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");
        deleteButton.setDisable(true); // اول غیر فعال چون چیزی انتخاب نشده

        deleteButton.setOnAction(e -> {
            Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
            if (selectedProduct != null) {
                boolean confirmed = showConfirmDialog("آیا مطمئن هستید می‌خواهید این کالا را حذف کنید؟");
                if (confirmed) {
                    boolean deleted = DeleteProduct.deleteProduct(selectedProduct.getTitle());
                    if (deleted) {
                        showSuccessMessage("کالا با موفقیت حذف شد.");
                        loadTableData();
                        deleteButton.setDisable(true);
                    } else {
                        showErrorMessage("خطا در حذف کالا.");
                    }
                }
            }
        });

        // فعال/غیرفعال کردن دکمه حذف بر اساس انتخاب جدول
        productTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            deleteButton.setDisable(newSelection == null);
        });

        content.getChildren().addAll(titleLabel, productTable, deleteButton);
    }

    private void createProductTable() {
        productTable = new TableView<>();
        productTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

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

        // راست‌چین ستون‌ها
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

        productTable.setPrefHeight(300);

        loadTableData();
    }

    private void loadTableData() {
        List<Product> products = ReadAllproduct.Readproduct();
        productTable.setItems(FXCollections.observableArrayList(products));
    }

    private boolean showConfirmDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("تایید حذف");
        alert.setHeaderText(null);
        alert.setContentText(message);

        ButtonType yesBtn = new ButtonType("بله");
        ButtonType noBtn = new ButtonType("خیر", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(yesBtn, noBtn);

        return alert.showAndWait().filter(buttonType -> buttonType == yesBtn).isPresent();
    }

    private void showSuccessMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("موفقیت");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showErrorMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("خطا");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public VBox getContent() {
        return content;
    }
}
