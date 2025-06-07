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
            content.getChildren().add(createShowCategoryForm());
        }

        Button backBut = new Button("بازگشت");
        backBut.setStyle("-fx-alignment: CENTER_RIGHT;-fx-padding: 5 10 5 5");
        backBut.setOnAction(e -> stage.close());
        content.getChildren().add(backBut);

        Scene scene = new Scene(content,600,400);
        stage.setScene(scene);
        stage.show();
    }

    private VBox createShowCategoryForm() {
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefViewportHeight(500);

        VBox form = new VBox();
        form.setAlignment(Pos.TOP_RIGHT);
        form.setPadding(new Insets(10));

        List<Category> categories = GetAllCategory.getAllCategory();
        List<Product> products = ReadAllproduct.Readproduct();

        for (Category category : categories){
            TitledPane titledPane = new TitledPane();
            titledPane.setText(category.getTitle());

            List<Product> categoryPruduct =  products.stream()
                    .filter(product -> product.getBrandTitle().equals(category.getTitle()))
                    .collect(Collectors.toList());

            if (categoryPruduct.isEmpty()){
                Label emptyLabel = new Label("هیچ محصولی در این برند وجود ندارد");
                titledPane.setContent(emptyLabel);
            } else {
                TableView<Product> table = new TableView<>();
                table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

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

                TableColumn<Product, String> brandCol = new TableColumn<>("برنذ ");
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

                table.setPrefHeight(Math.min(categoryPruduct.size() * 30 + 40, 200));

                titledPane.setContent(table);
            }

            form.getChildren().add(titledPane);
        }

        scrollPane.setContent(form);

        VBox mainContainer = new VBox(scrollPane);
        mainContainer.setPadding(new Insets(10));

        return mainContainer;
        }
    }