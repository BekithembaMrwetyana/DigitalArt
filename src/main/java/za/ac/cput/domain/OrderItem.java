package za.ac.cput.domain;
import jakarta.persistence.*;

/*
 OrderItem.java
 OrderItem POJO class
 Author : Thimna Gogwana 222213973
 Date: 25 May 2025
*/
@Entity
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemID;

    private Long orderID;

    @ManyToOne
    private Product product;

    private int quantity;
    private double unitPrice;
    private double subTotal;

    protected OrderItem() {
        // JPA requires protected no-args constructor
    }

    private OrderItem(Builder builder) {
        this.itemID = builder.itemID;
        this.orderID = builder.orderID;
        this.product = builder.product;
        this.quantity = builder.quantity;
        this.unitPrice = builder.unitPrice;
        this.subTotal = builder.subTotal;
    }

    public Long getItemID() {
        return itemID;
    }

    public Long getOrderID() {
        return orderID;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public double getSubTotal() {
        return subTotal;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "itemID=" + itemID +
                ", orderID=" + orderID +
                ", product=" + product +
                ", quantity=" + quantity +
                ", unitPrice=" + unitPrice +
                ", subTotal=" + subTotal +
                '}';
    }

    public static class Builder {

        private Long itemID;
        private Long orderID;
        private Product product;
        private int quantity;
        private double unitPrice;
        private double subTotal;

        public Builder setItemID(Long itemID) {
            this.itemID = itemID;
            return this;
        }

        public Builder setOrderID(Long orderID) {
            this.orderID = orderID;
            return this;
        }

        public Builder setProduct(Product product) {
            this.product = product; // FIXED: assign the passed product
            return this;
        }

        public Builder setQuantity(int quantity) {
            this.quantity = quantity;
            return this;
        }

        public Builder setUnitPrice(double unitPrice) {
            this.unitPrice = unitPrice;
            return this;
        }

        public Builder calculateSubTotal() {
            this.subTotal = this.unitPrice * this.quantity;
            return this;
        }

        public Builder setSubTotal(double subTotal) {
            this.subTotal = subTotal;
            return this;
        }

        public Builder copy(OrderItem item) {
            this.itemID = item.itemID;
            this.orderID = item.orderID;
            this.product = item.product;
            this.quantity = item.quantity;
            this.unitPrice = item.unitPrice;
            this.subTotal = item.subTotal;
            return this;
        }

        public OrderItem build() {
            // Auto-calculate subtotal if not explicitly set
            if (subTotal == 0 && unitPrice > 0 && quantity > 0) {
                subTotal = unitPrice * quantity;
            }
            return new OrderItem(this);
        }
    }
}




