package view;

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
import model.Product.brand.Brand;
import model.Product.brand.GetAllBrand;
import model.Product.brand.BrandInputToDB;
import model.Product.brand.DeleteBrand;
import model.Product.product.Product;
import model.Product.product.ReadAllproduct;
import runner.Main;

import java.util.List;
import java.util.stream.Collectors;

public class BrandManagement extends VBox {
    private VBox contentArea;
    private ListView<String> brandListView;

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
            content.getChildren().add(createAddDeleteBrandForm());
        } else if (title.equals("نمایش برند‌ها")) {
            content.getChildren().add(createShowBrandsForm());
        }

        Button backBtn = new Button("بازگشت");
        backBtn.setStyle("-fx-alignment: CENTER_RIGHT; -fx-padding: 5 10 5 5;");
        backBtn.setOnAction(e -> stage.close());
        content.getChildren().add(backBtn);

        Scene scene = new Scene(content, 600, 400);
        stage.setScene(scene);
        stage.show();
    }

    private VBox createAddDeleteBrandForm() {
        VBox form = new VBox(15);
        form.setAlignment(Pos.TOP_RIGHT);

        // بخش افزودن برند
        Label addLabel = new Label("افزودن برند جدید");
        addLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        TextField brandNameField = new TextField();
        brandNameField.setPromptText("نام برند را وارد کنید");
        brandNameField.setMaxWidth(300);

        Button addButton = new Button("افزودن");
        addButton.setOnAction(e -> {
            String brandName = brandNameField.getText().trim();
            if (!brandName.isEmpty()) {
                // بررسی تکراری نبودن برند
                List<Brand> existingBrands = GetAllBrand.getAllBrand();
                boolean isDuplicate = existingBrands.stream()
                        .anyMatch(brand -> brand.getTitle().equalsIgnoreCase(brandName));

                if (isDuplicate) {
                    Main.showAlert("خطا", "این برند قبلاً ثبت شده است!");
                } else {
                    Brand newBrand = new Brand(brandName);
                    BrandInputToDB.brandInput(newBrand);
                    Main.showAlert("موفقیت", "برند با موفقیت اضافه شد.");
                    brandNameField.clear();
                    updateBrandList(brandListView); // بروزرسانی لیست
                }
            } else {
                Main.showAlert("خطا", "لطفاً نام برند را وارد کنید.");
            }
        });// بخش نمایش و حذف برند‌ها
        Label listLabel = new Label("لیست برند‌ها");
        listLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        brandListView = new ListView<>();
        brandListView.setPrefHeight(200);
        updateBrandList(brandListView);

        Button deleteButton = new Button("حذف برند انتخاب شده");
        deleteButton.setStyle("-fx-background-color: #ff4444; -fx-text-fill: white;");
        deleteButton.setDisable(true);

        brandListView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            deleteButton.setDisable(newSelection == null);
        });

        deleteButton.setOnAction(e -> {
            String selectedBrand = brandListView.getSelectionModel().getSelectedItem();
            if (selectedBrand != null) {
                // بررسی استفاده از برند در محصولات
                List<Product> products = ReadAllproduct.Readproduct();
                boolean isUsed = products.stream()
                        .anyMatch(product -> product.getBrandTitle().equals(selectedBrand));

                if (isUsed) {
                    Main.showAlert("خطا", "این برند در محصولات استفاده شده است و نمی‌توان آن را حذف کرد.");
                } else {
                    DeleteBrand.deleteBrand(selectedBrand);
                    Main.showAlert("موفقیت", "برند با موفقیت حذف شد.");
                    updateBrandList(brandListView);
                }
            }
        });

        form.getChildren().addAll(
                addLabel,
                brandNameField,
                addButton,
                new Separator(),
                listLabel,
                brandListView,
                deleteButton
        );

        return form;
    }

    private void updateBrandList(ListView<String> listView) {
        List<Brand> brands = GetAllBrand.getAllBrand();
        ObservableList<String> brandNames = FXCollections.observableArrayList(
                brands.stream()
                        .map(Brand::getTitle)
                        .collect(Collectors.toList())
        );
        listView.setItems(brandNames);
    }

    private VBox createShowBrandsForm() {
        // ایجاد ScrollPane برای قابلیت اسکرول
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefViewportHeight(500); // ارتفاع پیش‌فرض برای اسکرول

        VBox form = new VBox(15);
        form.setAlignment(Pos.TOP_RIGHT);
        form.setPadding(new Insets(10));

        List<Brand> brands = GetAllBrand.getAllBrand();
        List<Product> products = ReadAllproduct.Readproduct();

        for (Brand brand : brands) {
            TitledPane brandPane = new TitledPane();
            brandPane.setText(brand.getTitle());

            // فیلتر کردن محصولات این برند
            List<Product> brandProducts = products.stream()
                    .filter(product -> product.getBrandTitle().equals(brand.getTitle()))
                    .collect(Collectors.toList());

            if (brandProducts.isEmpty()) {
                Label emptyLabel = new Label("هیچ محصولی در این برند وجود ندارد");
                brandPane.setContent(emptyLabel);
            } else {
                // ایجاد جدول محصولات
                TableView<Product> table = new TableView<>();
                table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

                // ستون نام محصول
                TableColumn<Product, String> titleCol = new TableColumn<>("نام محصول");
                titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
                titleCol.setPrefWidth(150);// ستون قیمت
                TableColumn<Product, Long> priceCol = new TableColumn<>("قیمت");
                priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
                priceCol.setPrefWidth(100);
                // فرمت کردن قیمت با جداکننده هزارگان
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

                // ستون موجودی
                TableColumn<Product, Long> quantityCol = new TableColumn<>("موجودی");
                quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
                quantityCol.setPrefWidth(80);

                // ستون دسته‌بندی
                TableColumn<Product, String> categoryCol = new TableColumn<>("دسته‌بندی");
                categoryCol.setCellValueFactory(new PropertyValueFactory<>("categoryTitle"));
                categoryCol.setPrefWidth(120);

                // ستون توضیحات
                TableColumn<Product, String> descriptionCol = new TableColumn<>("توضیحات");
                descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
                descriptionCol.setPrefWidth(200);

                // ستون تامین‌کننده
                TableColumn<Product, String> providerCol = new TableColumn<>("تامین‌کننده");
                providerCol.setCellValueFactory(new PropertyValueFactory<>("providerTitle"));
                providerCol.setPrefWidth(120);

                // تنظیم راست به چپ برای ستون‌ها
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

                // تنظیم داده‌های جدول
                table.setItems(FXCollections.observableArrayList(brandProducts));

                // تنظیم ارتفاع جدول بر اساس تعداد ردیف‌ها
                table.setPrefHeight(Math.min(brandProducts.size() * 30 + 40, 200));

                brandPane.setContent(table);
            }

            form.getChildren().add(brandPane);
        }

        scrollPane.setContent(form);

        // برگرداندن VBox اصلی که شامل ScrollPane است
        VBox mainContainer = new VBox(scrollPane);
        mainContainer.setPadding(new Insets(10));

        return mainContainer;
    }
}