package domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    private int orderID;

    private int userID;
    private double totalAmount;
    private LocalDateTime orderDate;
    private int paymentID;
    private String paymentStatus;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "orderID", referencedColumnName = "orderID")
    private List<OrderItem> orderItems;

    protected Order() {

    }

    private Order(Builder builder) {
        this.orderID = builder.orderID;
        this.userID = builder.userID;
        this.orderItems = builder.orderItems;
        this.totalAmount = builder.totalAmount;
        this.orderDate = builder.orderDate;
        this.paymentID = builder.paymentID;
        this.paymentStatus = builder.paymentStatus;
    }

    public int getOrderID() {
        return orderID;
    }

    public int getUserID() {
        return userID;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public int getPaymentID() {
        return paymentID;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderID=" + orderID +
                ", userID=" + userID +
                ", orderItems=" + orderItems +
                ", totalAmount=" + totalAmount +
                ", orderDate=" + orderDate +
                ", paymentID=" + paymentID +
                ", paymentStatus='" + paymentStatus + '\'' +
                '}';
    }

    public static class Builder {

        private int orderID;
        private int userID;
        private List<OrderItem> orderItems;
        private double totalAmount;
        private LocalDateTime orderDate;
        private int paymentID;
        private String paymentStatus;

        public Builder setOrderID(int orderID) {
            this.orderID = orderID;
            return this;
        }

        public Builder setUserID(int userID) {
            this.userID = userID;
            return this;
        }

        public Builder setOrderItems(List<OrderItem> orderItems) {
            this.orderItems = orderItems;
            return this;
        }

        public Builder setTotalAmount(double totalAmount) {
            this.totalAmount = totalAmount;
            return this;
        }

        public Builder setOrderDate(LocalDateTime orderDate) {
            this.orderDate = orderDate;
            return this;
        }

        public Builder setPaymentID(int paymentID) {
            this.paymentID = paymentID;
            return this;
        }

        public Builder setPaymentStatus(String paymentStatus) {
            this.paymentStatus = paymentStatus;
            return this;
        }

        public Builder copy(Order order) {
            this.orderID = order.orderID;
            this.userID = order.userID;
            this.orderItems = order.orderItems;
            this.totalAmount = order.totalAmount;
            this.orderDate = order.orderDate;
            this.paymentID = order.paymentID;
            this.paymentStatus = order.paymentStatus;
            return this;
        }

        public Order build() {
            return new Order(this);
        }
    }
}
