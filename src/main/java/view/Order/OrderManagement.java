package view.Order;

import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.Order.*;
import model.Product.product.Product;

import java.util.List;

public class OrderManagement extends VBox {
    private VBox contentArea;
    private TableView<Order> orderTable;
    private TableView<Product> productTable;
    private Order currentOrder;
    private Customer currentCustomer;

    public OrderManagement(VBox contentArea) {
        super(5);
        this.contentArea = contentArea;
        setVisible(false);
        setManaged(false);
        setStyle("-fx-padding:0 35 0 0;");
        setAlignment(Pos.TOP_RIGHT);
        createOrderManagementMenu();
    }

    private void createOrderManagementMenu() {
        String buttonStyle = "-fx-background-color: rgba(175, 180, 204, 0.58); -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10 20; -fx-background-radius: 5; -fx-alignment: CENTER_RIGHT; -fx-content-display: RIGHT;";
        String buttonHoverStyle = "-fx-background-color: rgba(175, 180, 204, 0.58); -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10 20; -fx-background-radius: 5; -fx-effect: dropshadow(gaussian, #a6cbeb, 10, 0.5, 0, 0); -fx-alignment: CENTER_RIGHT; -fx-content-display: RIGHT;";

        Button addOrderBtn = createOrderButton("افزودن سفارش", "/images/AddProduct.png", buttonStyle, buttonHoverStyle);
        Button viewOrdersBtn = createOrderButton("نمایش سفارش‌ها", "/images/ShowProduct.png", buttonStyle, buttonHoverStyle);

        addOrderBtn.setPrefWidth(240);
        viewOrdersBtn.setPrefWidth(240);

        addOrderBtn.setOnAction(e -> showCustomerForm());
        viewOrdersBtn.setOnAction(e -> showOrders());

        getChildren().addAll(addOrderBtn, viewOrdersBtn);
    }

    private Button createOrderButton(String text, String imagePath, String buttonStyle, String buttonHoverStyle) {
        ImageView icon = new ImageView(new Image(getClass().getResourceAsStream(imagePath)));
        icon.setFitWidth(35);
        icon.setFitHeight(35);

        Label textLabel = new Label(text);
        textLabel.setFont(javafx.scene.text.Font.font("System", 14));
        textLabel.setTextFill(javafx.scene.paint.Color.WHITE);

        HBox buttonContent = new HBox(10);
        buttonContent.setAlignment(Pos.CENTER_LEFT);
        buttonContent.getChildren().addAll(icon, textLabel);
        buttonContent.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);

        Button button = new Button();
        button.setGraphic(buttonContent);
        button.setStyle(buttonStyle);

        button.setOnMouseEntered(e -> button.setStyle(buttonHoverStyle));
        button.setOnMouseExited(e -> button.setStyle(buttonStyle));

        return button;
    }

    private void showCustomerForm() {
        contentArea.getChildren().clear();

        VBox form = new VBox(10);
        form.setAlignment(Pos.CENTER);
        form.setPadding(new Insets(20));

        Label customerInfoTitle = new Label("اطلاعات مشتری");
        customerInfoTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: white;");

        TextField firstNameField = new TextField();
        firstNameField.setPromptText("نام");
        firstNameField.setStyle("-fx-control-inner-background: rgba(175, 180, 204, 0.58); -fx-text-fill: white; -fx-prompt-text-fill: white; -fx-font-size: 14px;");
        firstNameField.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);

        TextField lastNameField = new TextField();
        lastNameField.setPromptText("نام خانوادگی");
        lastNameField.setStyle("-fx-control-inner-background: rgba(175, 180, 204, 0.58); -fx-text-fill: white; -fx-prompt-text-fill: white; -fx-font-size: 14px;");
        lastNameField.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);

        TextField phoneField = new TextField();
        phoneField.setPromptText("شماره تلفن");
        phoneField.setStyle("-fx-control-inner-background: rgba(175, 180, 204, 0.58); -fx-text-fill: white; -fx-prompt-text-fill: white; -fx-font-size: 14px;");
        phoneField.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);

        Button submitBtn = new Button("تایید");
        submitBtn.setStyle("-fx-background-color:rgb(2, 75, 2); -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10 20; -fx-background-radius: 5;");

        form.getChildren().addAll(
                customerInfoTitle,
                firstNameField,
                lastNameField,
                phoneField,
                submitBtn
        );

        submitBtn.setOnAction(e -> {
            try {
                Customer customer = new Customer(
                        firstNameField.getText(),
                        lastNameField.getText(),
                        phoneField.getText()
                );
                int customerId = CustomerInputToDB.customer(customer);
                customer.setId(customerId);
                currentCustomer = customer;
                currentOrder = new Order(customerId);
                showProductSelection();
            } catch (Exception ex) {
                showError("خطا در ذخیره اطلاعات مشتری: " + ex.getMessage());
            }
        });

        contentArea.getChildren().add(form);
    }

    private void showProductSelection() {
        contentArea.getChildren().clear();

        VBox orderBox = new VBox(10);
        orderBox.setAlignment(Pos.TOP_RIGHT);
        orderBox.setPadding(new Insets(20));
        orderBox.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);

        // نمایش اطلاعات مشتری
        Label customerInfo = new Label(String.format("مشتری: %s %s - تلفن: %s",
                currentCustomer.getFirstName(),
                currentCustomer.getLastName(),
                currentCustomer.getPhoneNumber()));
        customerInfo.setStyle("-fx-font-size: 14px; -fx-text-fill: white;");

        Label selectProductLabel = new Label("انتخاب محصولات");
        selectProductLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: white;");

        // جدول محصولات
        productTable = new TableView<>();
        productTable.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        TableColumn<Product, String> nameCol = new TableColumn<>("نام محصول");
        nameCol.setStyle("-fx-alignment: CENTER_RIGHT;");
        TableColumn<Product, Double> priceCol = new TableColumn<>("قیمت");
        priceCol.setStyle("-fx-alignment: CENTER_RIGHT;");
        productTable.getColumns().addAll(nameCol, priceCol);
        productTable.setPrefHeight(200);
        productTable.setStyle("-fx-control-inner-background: white; -fx-font-size: 14px; -fx-text-fill: black;");

        // اضافه کردن محصول به سفارش
        HBox addItemBox = new HBox(10);
        addItemBox.setAlignment(Pos.CENTER_RIGHT);
        Label quantityLabel = new Label("تعداد:");
        quantityLabel.setStyle("-fx-text-fill: white; -fx-font-size: 14px;");
        Spinner<Integer> quantitySpinner = new Spinner<>(1, 100, 1);
        quantitySpinner.setPrefWidth(80);
        quantitySpinner.setStyle("-fx-control-inner-background: rgba(175, 180, 204, 0.58); -fx-text-fill: white; -fx-font-size: 14px;");
        quantitySpinner.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        Button addToOrderBtn = new Button("افزودن به سفارش");
        addToOrderBtn.setStyle("-fx-background-color:rgb(2, 75, 2); -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 5 15; -fx-background-radius: 5;");

        addToOrderBtn.setOnAction(e -> {
            Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
            if (selectedProduct != null) {
                OrderItem item = new OrderItem(
                        (int) selectedProduct.getId(),
                        quantitySpinner.getValue(),
                        selectedProduct.getPrice()
                );
                currentOrder.addOrderItem(item);
                updateOrderSummary();
            }
        });
        addItemBox.getChildren().addAll(addToOrderBtn, quantitySpinner, quantityLabel);

        // خلاصه سفارش
        VBox orderSummary = new VBox(10);
        orderSummary.setAlignment(Pos.CENTER_RIGHT);
        Label totalLabel = new Label("مجموع: 0 ریال");
        totalLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: white;");

        Button finalizeOrderBtn = new Button("ثبت نهایی سفارش");
        finalizeOrderBtn.setStyle("-fx-background-color: #007bff; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10 20; -fx-background-radius: 5;");

        finalizeOrderBtn.setOnAction(e -> {
            try {
                int orderId = OrderInputToDB.order(currentOrder);
                if (orderId != -1) {
                    OrderItemInputToDB.orderItem(orderId, currentOrder.getOrderItems());
                    showSuccess("سفارش با موفقیت ثبت شد");
                    showOrders();
                }
            } catch (Exception ex) {
                showError("خطا در ثبت سفارش: " + ex.getMessage());
            }
        });

        orderBox.getChildren().addAll(
                customerInfo,
                selectProductLabel,
                productTable,
                addItemBox,
                orderSummary,
                finalizeOrderBtn
        );

        contentArea.getChildren().add(orderBox);
        loadProducts();
    }

    private void showOrders() {
        contentArea.getChildren().clear();

        VBox ordersBox = new VBox(10);
        ordersBox.setAlignment(Pos.TOP_RIGHT);
        ordersBox.setPadding(new Insets(20));
        ordersBox.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);

        Label ordersListTitle = new Label("لیست سفارش‌ها");
        ordersListTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: white;");

        orderTable = new TableView<>();
        orderTable.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        TableColumn<Order, String> dateCol = new TableColumn<>("تاریخ");
        dateCol.setStyle("-fx-alignment: CENTER_RIGHT;");
        TableColumn<Order, String> customerCol = new TableColumn<>("مشتری");
        customerCol.setStyle("-fx-alignment: CENTER_RIGHT;");
        TableColumn<Order, Double> totalCol = new TableColumn<>("مبلغ کل");
        totalCol.setStyle("-fx-alignment: CENTER_RIGHT;");
        orderTable.getColumns().addAll(dateCol, customerCol, totalCol);
        orderTable.setPrefHeight(300);
        orderTable.setStyle("-fx-control-inner-background: white; -fx-font-size: 14px; -fx-text-fill: black;");

        ordersBox.getChildren().addAll(
                ordersListTitle,
                orderTable
        );

        contentArea.getChildren().add(ordersBox);
        loadOrders();
    }

    private void loadProducts() {
        // TODO: Load products from database
    }

    private void loadOrders() {
        try {
            List<Order> orders = GetfFeatureOrder.getAllOrder();
            orderTable.getItems().setAll(orders);
        } catch (Exception e) {
            showError("خطا در بارگذاری سفارش‌ها: " + e.getMessage());
        }
    }

    private void updateOrderSummary() {
        // TODO: Update order summary display
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("خطا");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showSuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("موفقیت");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}