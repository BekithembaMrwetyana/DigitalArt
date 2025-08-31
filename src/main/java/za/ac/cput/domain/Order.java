package za.ac.cput.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import za.ac.cput.domain.enums.OrderStatus;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderID;

   @Column(updatable = false)
    private double totalAmount;
    @Column(updatable = false)
   private double orderAmount;
    private LocalDateTime orderDate;

    @Embedded
    private Address shippingAddress;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "userId", nullable = true)
    private User user;

    @Enumerated(EnumType.STRING)
    private OrderStatus paymentStatus;



    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "orderID", referencedColumnName = "orderID")
    private List<CartItem> cartItems;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    protected Order() {}

    public Order(Builder builder) {
        this.orderID = builder.orderID;

        this.cartItems = builder.cartItems;
        this.totalAmount = builder.totalAmount;
        this.orderDate = builder.orderDate;
        this.orderAmount = builder.orderAmount;
        this.paymentStatus = builder.paymentStatus;
    }

    public Long getOrderID() {
        return orderID;
    }

    public void setId(Long id) {
        this.orderID = id;
    }


    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public double getTotalAmount() {
        return totalAmount;
    }
    public double getOrderAmount() {
        return orderAmount;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }


    public OrderStatus getPaymentStatus() {
        return paymentStatus;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderID=" + orderID +

                //", cartItems=" + cartItems +
                ", totalAmount=" + totalAmount +
                ", orderDate=" + orderDate +
                " orderAmount=" + orderAmount +

                ", paymentStatus=" + paymentStatus +
                '}';
    }

    public static class Builder {

        private Long orderID;


        private double totalAmount;
        private double orderAmount;
        private LocalDateTime orderDate;


        private OrderStatus paymentStatus;
        private List<CartItem> cartItems;
        private User user;

        public Builder setOrderID(Long orderID) {
            this.orderID = orderID;
            return this;
        }


        public Builder setCartItem(List<CartItem> cartItems) {
            this.cartItems = cartItems;
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
        public Builder setOrderAmount(double orderAmount) {
            this.orderAmount = orderAmount;
            return this;
        }



        public Builder setPaymentStatus(OrderStatus paymentStatus) {
            this.paymentStatus = paymentStatus;
            return this;
        }

        public Builder copy(Order order) {
            this.orderID = order.orderID;

            this.cartItems = order.cartItems;
            this.totalAmount = order.totalAmount;
            this.orderDate = order.orderDate;
            this.orderAmount = order.orderAmount;

            this.paymentStatus = order.paymentStatus;
            return this;
        }

        public Order build() {
            Order order = new Order(this);
            order.user = this.user;
            return order;
            //return new Order(this);


        }
    }
}
