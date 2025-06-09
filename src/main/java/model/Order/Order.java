package model.Order;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private int id;
    private int customerId;
    private LocalDateTime orderDate;
    private double totalAmount;
    private List<OrderItem> orderItems;

    public Order(int customerId) {
        this.customerId = customerId;
        this.orderDate = LocalDateTime.now();
        this.orderItems = new ArrayList<>();
        this.totalAmount = 0.0;
    }

    public Order(int id, int customerId, LocalDateTime orderDate, double totalAmount) {
        this.id = id;
        this.customerId = customerId;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.orderItems = new ArrayList<>();
    }

    public void addOrderItem(OrderItem item) {
        orderItems.add(item);
        calculateTotalAmount();
    }

    private void calculateTotalAmount() {
        this.totalAmount = orderItems.stream()
                .mapToDouble(item -> item.getQuantity() * item.getUnitPrice())
                .sum();
    }

    // Getters
    public int getId() { return id; }
    public int getCustomerId() { return customerId; }
    public LocalDateTime getOrderDate() { return orderDate; }
    public double getTotalAmount() { return totalAmount; }
    public List<OrderItem> getOrderItems() { return orderItems; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }
    public void setOrderDate(LocalDateTime orderDate) { this.orderDate = orderDate; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }
}