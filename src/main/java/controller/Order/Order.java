package controller.Order;

import java.time.LocalDateTime;
import java.util.List;

public class Order {
    private int id;
    private int customerId;
    private LocalDateTime orderDate;
    private double totalAmount;
    private List<Order_item> order_items;

    public Order(int id, int customerId, LocalDateTime orderDate, double totalAmount, List<Order_item> order_items) {
        this.id = id;
        this.customerId = customerId;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.order_items = order_items;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<Order_item> getOrder_items() {
        return order_items;
    }

    public void setOrder_items(List<Order_item> order_items) {
        this.order_items = order_items;
    }
}
