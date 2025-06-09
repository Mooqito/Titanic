package view.Order;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
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
        this.contentArea = contentArea;
        setSpacing(10);
        setAlignment(Pos.TOP_RIGHT);
        createOrderManagementMenu();
    }

    private void createOrderManagementMenu() {
        Button addOrderBtn = new Button("افزودن سفارش");
        Button viewOrdersBtn = new Button("نمایش سفارش‌ها");

        addOrderBtn.setOnAction(e -> showCustomerForm());
        viewOrdersBtn.setOnAction(e -> showOrders());

        getChildren().addAll(addOrderBtn, viewOrdersBtn);
    }

    private void showCustomerForm() {
        contentArea.getChildren().clear();

        VBox form = new VBox(10);
        form.setAlignment(Pos.CENTER);
        form.setPadding(new Insets(20));

        TextField firstNameField = new TextField();
        firstNameField.setPromptText("نام");
        TextField lastNameField = new TextField();
        lastNameField.setPromptText("نام خانوادگی");
        TextField phoneField = new TextField();
        phoneField.setPromptText("شماره تلفن");

        Button submitBtn = new Button("تایید");
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

        form.getChildren().addAll(
                new Label("اطلاعات مشتری"),
                firstNameField,
                lastNameField,
                phoneField,
                submitBtn
        );

        contentArea.getChildren().add(form);
    }

    private void showProductSelection() {
        contentArea.getChildren().clear();

        VBox orderBox = new VBox(10);
        orderBox.setAlignment(Pos.CENTER);
        orderBox.setPadding(new Insets(20));

        // نمایش اطلاعات مشتری
        Label customerInfo = new Label(String.format("مشتری: %s %s - تلفن: %s",
                currentCustomer.getFirstName(),
                currentCustomer.getLastName(),
                currentCustomer.getPhoneNumber()));

        // جدول محصولات
        productTable = new TableView<>();
        TableColumn<Product, String> nameCol = new TableColumn<>("نام محصول");
        TableColumn<Product, Double> priceCol = new TableColumn<>("قیمت");
        productTable.getColumns().addAll(nameCol, priceCol);

        // اضافه کردن محصول به سفارش
        HBox addItemBox = new HBox(10);
        Spinner<Integer> quantitySpinner = new Spinner<>(1, 100, 1);
        Button addToOrderBtn = new Button("افزودن به سفارش");
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
        addItemBox.getChildren().addAll(new Label("تعداد:"), quantitySpinner, addToOrderBtn);
        // خلاصه سفارش
        VBox orderSummary = new VBox(10);
        Label totalLabel = new Label("مجموع: 0 ریال");

        Button finalizeOrderBtn = new Button("ثبت نهایی سفارش");
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
                new Label("انتخاب محصولات"),
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
        ordersBox.setAlignment(Pos.CENTER);
        ordersBox.setPadding(new Insets(20));

        orderTable = new TableView<>();
        TableColumn<Order, String> dateCol = new TableColumn<>("تاریخ");
        TableColumn<Order, String> customerCol = new TableColumn<>("مشتری");
        TableColumn<Order, Double> totalCol = new TableColumn<>("مبلغ کل");
        orderTable.getColumns().addAll(dateCol, customerCol, totalCol);

        ordersBox.getChildren().addAll(
                new Label("لیست سفارش‌ها"),
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