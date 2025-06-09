package view.Category;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Priority;
import model.Product.category.Category;
import model.Product.category.GetAllCategory;
import model.Product.product.Product;
import model.Product.product.ReadAllproduct;
import javafx.geometry.NodeOrientation;

import java.util.List;
import java.util.stream.Collectors;

public class ShowCategoryList {


    public VBox createShowCategoryForm() {
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefViewportHeight(500);

        VBox form = new VBox(15);
        form.setAlignment(Pos.CENTER);
        form.setPadding(new Insets(10));
        form.setPrefWidth(Double.MAX_VALUE);
        form.setMaxWidth(Double.MAX_VALUE);

        List<Category> categories = GetAllCategory.getAllCategory();
        List<Product> products = ReadAllproduct.Readproduct();

        for (Category category : categories){
            TitledPane titledPane = new TitledPane();
            titledPane.setText(category.getTitle());
            titledPane.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);

            List<Product> categoryPruduct =  products.stream()
                    .filter(product -> product.getCategoryTitle().equals(category.getTitle()))
                    .collect(Collectors.toList());

            if (categoryPruduct.isEmpty()){
                Label emptyLabel = new Label("هیچ محصولی در این برند وجود ندارد");
                titledPane.setContent(emptyLabel);
            } else {
                TableView<Product> table = new TableView<>();
                table.setMaxWidth(950);
                table.setPrefHeight(Math.min(categoryPruduct.size() * 30 + 30, 400));
                table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

                VBox.setVgrow(table, Priority.NEVER);

                TableColumn<Product, String> titleCol = new TableColumn<>("نام محصول");
                titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
                titleCol.setPrefWidth(150);// ستون قیمت
                TableColumn<Product, Long> priceCol = new TableColumn<>("قیمت");
                priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
                priceCol.setPrefWidth(100);
                priceCol.setCellFactory(column -> new TableCell<Product, Long>() {
                    @Override
                    protected void updateItem(Long price, boolean empty) {
                        super.updateItem(price, empty);
                        if (empty || price == null) {
                            setText(null);
                        } else {
                            setText(String.format("%,d", price));
                        }
                    }
                });
                TableColumn<Product, Long> quantityCol = new TableColumn<>("موجودی");
                quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
                quantityCol.setPrefWidth(80);

                TableColumn<Product, String> brandCol = new TableColumn<>("برند ");
                brandCol.setCellValueFactory(new PropertyValueFactory<>("brandTitle"));
                brandCol.setPrefWidth(120);

                TableColumn<Product, String> descriptionCol = new TableColumn<>("توضیحات");
                descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
                descriptionCol.setPrefWidth(200);

                TableColumn<Product, String> providerCol = new TableColumn<>("تامین‌کننده");
                providerCol.setCellValueFactory(new PropertyValueFactory<>("providerTitle"));
                providerCol.setPrefWidth(120);

                titleCol.setStyle("-fx-alignment: CENTER-RIGHT;");
                priceCol.setStyle("-fx-alignment: CENTER-RIGHT;");
                quantityCol.setStyle("-fx-alignment: CENTER-RIGHT;");
                brandCol.setStyle("-fx-alignment: CENTER-RIGHT;");
                descriptionCol.setStyle("-fx-alignment: CENTER-RIGHT;");
                providerCol.setStyle("-fx-alignment: CENTER-RIGHT;");

                table.getColumns().addAll(
                        titleCol, priceCol, quantityCol, brandCol,
                        providerCol, descriptionCol
                );

                table.setItems(FXCollections.observableArrayList(categoryPruduct));

                titledPane.setContent(table);
            }

            form.getChildren().add(titledPane);
            VBox.setVgrow(titledPane, Priority.NEVER);
        }

        scrollPane.setContent(form);

        VBox mainContainer = new VBox(scrollPane);
        mainContainer.setPadding(new Insets(10));
        mainContainer.setAlignment(Pos.CENTER);
        VBox.setVgrow(mainContainer, Priority.ALWAYS);

        return mainContainer;
    }
}