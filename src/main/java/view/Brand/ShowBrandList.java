package view.Brand;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Priority;
import model.Product.brand.Brand;
import model.Product.brand.GetAllBrand;
import model.Product.product.Product;
import model.Product.product.ReadAllproduct;

import java.util.List;
import java.util.stream.Collectors;

public class ShowBrandList {

    private VBox contentArea;
    private BrandManagement parentManagement;

    public ShowBrandList(VBox contentArea, BrandManagement parentManagement) {
        this.contentArea = contentArea;
        this.parentManagement = parentManagement;
    }

    public VBox createShowBrandsForm() {
        // Create ScrollPane for scrolling capability
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefViewportHeight(500); // Default height for scrolling

        VBox form = new VBox(15);
        form.setAlignment(Pos.CENTER);
        form.setPadding(new Insets(10));
        form.setPrefWidth(Double.MAX_VALUE);
        form.setMaxWidth(Double.MAX_VALUE);

        List<Brand> brands = GetAllBrand.getAllBrand();
        List<Product> products = ReadAllproduct.Readproduct();

        for (Brand brand : brands) {
            TitledPane brandPane = new TitledPane();
            brandPane.setText(brand.getTitle());
            brandPane.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);

            // Filter products of this brand
            List<Product> brandProducts = products.stream()
                    .filter(product -> product.getBrandTitle().equals(brand.getTitle()))
                    .collect(Collectors.toList());

            if (brandProducts.isEmpty()) {
                Label emptyLabel = new Label("هیچ محصولی در این برند وجود ندارد");
                brandPane.setContent(emptyLabel);
            } else {
                // Create products table
                TableView<Product> table = new TableView<>();
                table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

                // Product name column
                TableColumn<Product, String> titleCol = new TableColumn<>("نام محصول");
                titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
                titleCol.setPrefWidth(150);// Price column
                TableColumn<Product, Long> priceCol = new TableColumn<>("قیمت");
                priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
                priceCol.setPrefWidth(100);
                // Format price with thousand separator
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

                // Stock column
                TableColumn<Product, Long> quantityCol = new TableColumn<>("موجودی");
                quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
                quantityCol.setPrefWidth(80);

                // Category column
                TableColumn<Product, String> categoryCol = new TableColumn<>("دسته‌بندی");
                categoryCol.setCellValueFactory(new PropertyValueFactory<>("categoryTitle"));
                categoryCol.setPrefWidth(120);

                // Description column
                TableColumn<Product, String> descriptionCol = new TableColumn<>("توضیحات");
                descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
                descriptionCol.setPrefWidth(200);

                // Supplier column
                TableColumn<Product, String> providerCol = new TableColumn<>("تامین‌کننده");
                providerCol.setCellValueFactory(new PropertyValueFactory<>("providerTitle"));
                providerCol.setPrefWidth(120);

                // Set right-to-left for columns
                titleCol.setStyle("-fx-alignment: CENTER-RIGHT;");
                priceCol.setStyle("-fx-alignment: CENTER-RIGHT;");
                quantityCol.setStyle("-fx-alignment: CENTER-RIGHT;");
                categoryCol.setStyle("-fx-alignment: CENTER-RIGHT;");
                descriptionCol.setStyle("-fx-alignment: CENTER-RIGHT;");
                providerCol.setStyle("-fx-alignment: CENTER-RIGHT;");

                table.getColumns().addAll(
                        titleCol, priceCol, quantityCol, categoryCol,
                        providerCol, descriptionCol
                );

                // Set table data
                table.setItems(FXCollections.observableArrayList(brandProducts));

                // Set table height based on number of rows
                table.setPrefHeight(Math.min(brandProducts.size() * 30 + 40, 200));

                brandPane.setContent(table);
            }

            form.getChildren().add(brandPane);
        }

        scrollPane.setContent(form);

        // Return main VBox containing ScrollPane
        VBox mainContainer = new VBox(scrollPane);
        mainContainer.setPadding(new Insets(10));
        mainContainer.setAlignment(Pos.CENTER);
        VBox.setVgrow(mainContainer, Priority.ALWAYS);

        return mainContainer;
    }
}