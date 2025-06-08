package view.Brand;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import model.Product.brand.Brand;
import model.Product.brand.BrandInputToDB;
import model.Product.brand.DeleteBrand;
import model.Product.brand.GetAllBrand;
import model.Product.product.Product;
import model.Product.product.ReadAllproduct;
import runner.Main;
import javafx.animation.PauseTransition;
import javafx.util.Duration;
import javafx.stage.Stage;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Insets;

import java.util.List;
import java.util.stream.Collectors;

public class AddDeleteBrand {
    private VBox contentArea;
    private BrandManagement brandManagement;
    private ListView<String> brandListView;
    private BrandService brandService;
    private Button deleteButton;

    private class BrandService {
        public boolean addBrand(String brandName) {
            List<Brand> existingBrands = GetAllBrand.getAllBrand();
            boolean isDuplicate = existingBrands.stream()
                    .anyMatch(brand -> brand.getTitle().equalsIgnoreCase(brandName));

            if (!isDuplicate) {
                Brand newBrand = new Brand(brandName);
                BrandInputToDB.brandInput(newBrand);
                return true;
            }
            return false;
        }
    }

    public AddDeleteBrand(VBox contentArea, BrandManagement brandManagement) {
        this.contentArea = contentArea;
        this.brandManagement = brandManagement;
        this.brandService = new BrandService();
        this.brandListView = new ListView<>();
        this.deleteButton = new Button(" حذف برند انتخاب شده");
        this.deleteButton.setDisable(true);

        // Set Listener for enabling/disabling delete button
        brandListView.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("Selected index: " + newValue); // For debugging
            deleteButton.setDisable(newValue.intValue() == -1);
        });

        updateBrandList(brandListView);
    }

    public VBox createAddDeleteBrandForm() {
        VBox form = new VBox(15);
        form.setAlignment(Pos.TOP_RIGHT);
        form.setStyle("-fx-background-color: #3a5c79; -fx-padding: 20;");
        form.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
        form.setPrefWidth(Double.MAX_VALUE);
        form.setMaxWidth(Double.MAX_VALUE);

        // Add brand section
        Label addLabel = new Label("افزودن برند جدید");
        addLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: white; -fx-text-alignment: right;");
        addLabel.setTextFill(javafx.scene.paint.Color.WHITE);

        TextField brandNameField = new TextField();
        brandNameField.setPromptText("نام برند را وارد کنید");
        brandNameField.setMaxWidth(300);
        brandNameField.setStyle("-fx-background-color: #52799b; -fx-text-fill: white; -fx-text-alignment: right;");
        brandNameField.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);

        Button addButton = new Button("افزودن");
        addButton.setStyle("-fx-background-color:rgb(2, 75, 2); -fx-text-fill: white; -fx-alignment: CENTER_RIGHT;");
        deleteButton.setStyle("-fx-background-color: #ff4444; -fx-text-fill: white; -fx-alignment: CENTER_RIGHT;");
        deleteButton.setDisable(true);

        Label errorMessageLabel = new Label();
        errorMessageLabel.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
        errorMessageLabel.setVisible(false);
        errorMessageLabel.setManaged(true);
        errorMessageLabel.setMinWidth(120);

        Label successMessageLabel = new Label();
        successMessageLabel.setStyle("-fx-text-fill: #2ecc71;"); // Green color
        successMessageLabel.setVisible(false);
        successMessageLabel.setManaged(true);
        successMessageLabel.setMinWidth(120);

        // Success message for brand deletion
        Label deleteSuccessMessage = new Label();
        deleteSuccessMessage.setStyle("-fx-text-fill: #2ecc71;"); // Green color
        deleteSuccessMessage.setVisible(false);
        deleteSuccessMessage.setManaged(true);
        deleteSuccessMessage.setMinWidth(120);

        HBox buttonBox = new HBox(10, errorMessageLabel, successMessageLabel, addButton);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);

        addButton.setOnAction(e -> {
            String brandName = brandNameField.getText().trim();
            if (brandName.isEmpty()) {
                errorMessageLabel.setText("لطفا نام برند را وارد کنید");
                errorMessageLabel.setVisible(true);
                errorMessageLabel.setManaged(true);
                successMessageLabel.setVisible(false);
                successMessageLabel.setManaged(false);

                // Hide error message after 3 seconds
                PauseTransition delay = new PauseTransition(Duration.seconds(3));
                delay.setOnFinished(event -> {
                    errorMessageLabel.setVisible(false);
                    errorMessageLabel.setManaged(false);
                });
                delay.play();
            } else {
                if (brandService.addBrand(brandName)) {
                    brandNameField.clear();
                    errorMessageLabel.setVisible(false);
                    errorMessageLabel.setManaged(false);
                    successMessageLabel.setText("برند با موفقیت اضافه شد");
                    successMessageLabel.setVisible(true);
                    successMessageLabel.setManaged(true);
                    updateBrandList(brandListView);

                    // Hide success message after 3 seconds
                    PauseTransition delay = new PauseTransition(Duration.seconds(3));
                    delay.setOnFinished(event -> {
                        successMessageLabel.setVisible(false);
                        successMessageLabel.setManaged(false);
                    });
                    delay.play();
                } else {
                    errorMessageLabel.setText("این برند قبلا ثبت شده است");
                    errorMessageLabel.setVisible(true);
                    errorMessageLabel.setManaged(true);
                    successMessageLabel.setVisible(false);
                    successMessageLabel.setManaged(false);

                    // Hide error message after 3 seconds
                    PauseTransition delay = new PauseTransition(Duration.seconds(3));
                    delay.setOnFinished(event -> {
                        errorMessageLabel.setVisible(false);
                        errorMessageLabel.setManaged(false);
                    });
                    delay.play();
                }
            }
        });

        // Brand list and deletion section
        Label listLabel = new Label("لیست برند‌ها");
        listLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: white; -fx-text-alignment: right;");
        listLabel.setTextFill(javafx.scene.paint.Color.WHITE);

        brandListView.setPrefHeight(500);
        brandListView.setPrefWidth(900);
        brandListView.setMaxWidth(900);
        brandListView.setStyle("-fx-background-color:rgb(108, 148, 183);");
        brandListView.setCellFactory(lv -> new ListCell<String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                    setStyle("");
                    deleteButton.setDisable(true);
                } else {
                    Label cellLabel = new Label(item);
                    cellLabel.setStyle("-fx-text-fill: black; -fx-font-size: 14px;");
                    cellLabel.setTextAlignment(TextAlignment.RIGHT);
                    cellLabel.setMaxWidth(Double.MAX_VALUE);
                    HBox.setHgrow(cellLabel, Priority.ALWAYS);

                    HBox graphicContainer = new HBox(cellLabel);
                    graphicContainer.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
                    graphicContainer.setMaxWidth(Double.MAX_VALUE);
                    graphicContainer.setStyle("-fx-padding: 5 0 5 0;");

                    setGraphic(graphicContainer);
                    setText(null);
                    deleteButton.setDisable(false);
                }
            }
        });
        updateBrandList(brandListView);

        // Create HBox for delete button and success message
        HBox deleteBox = new HBox(10);
        deleteBox.setAlignment(Pos.CENTER_LEFT);
        deleteBox.getChildren().addAll(deleteButton, deleteSuccessMessage);

        deleteButton.setOnAction(e -> {
            String selectedBrand = brandListView.getSelectionModel().getSelectedItem();
            if (selectedBrand != null) {
                // Check if brand is in use in products
                List<Product> products = ReadAllproduct.Readproduct();
                boolean isBrandInUse = products.stream()
                        .anyMatch(product -> product.getBrandTitle().equals(selectedBrand));

                if (isBrandInUse) {
                    errorMessageLabel.setText("این برند در محصولات استفاده شده و قابل حذف نیست");
                    errorMessageLabel.setVisible(true);
                    errorMessageLabel.setManaged(true);
                    deleteSuccessMessage.setVisible(false);
                    deleteSuccessMessage.setManaged(false);

                    // Hide error message after 3 seconds
                    PauseTransition delay = new PauseTransition(Duration.seconds(3));
                    delay.setOnFinished(event -> {
                        errorMessageLabel.setVisible(false);
                        errorMessageLabel.setManaged(false);
                    });
                    delay.play();
                } else {
                    // Delete brand
                    DeleteBrand.deleteBrand(selectedBrand);

                    // Show success message
                    deleteSuccessMessage.setText("برند با موفقیت حذف شد");
                    deleteSuccessMessage.setVisible(true);
                    deleteSuccessMessage.setManaged(true);
                    errorMessageLabel.setVisible(false);
                    errorMessageLabel.setManaged(false);

                    // Hide success message after 3 seconds
                    PauseTransition delay = new PauseTransition(Duration.seconds(3));
                    delay.setOnFinished(event -> {
                        deleteSuccessMessage.setVisible(false);
                        deleteSuccessMessage.setManaged(false);
                    });
                    delay.play();

                    // Update list
                    updateBrandList(brandListView);
                }
            }
        });

        form.getChildren().addAll(
                addLabel,
                brandNameField,
                buttonBox,
                new Separator(),
                listLabel,
                brandListView,
                deleteBox
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
}