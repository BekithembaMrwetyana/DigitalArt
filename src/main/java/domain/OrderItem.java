package domain;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
/*
 OrderItem.java
 OrderItem POJO class
 Author : Thimna Gogwana 222213973
 Date: 25 May 2025
*/
@Entity
@Table
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int itemID;
    private int orderID;

    @ManyToOne
    private Product product;

    
    private int quantity;
    private double unitPrice;
    private double subTotal;
    
    protected OrderItem(){
        
    }

    private OrderItem(Builder builder) {
        this.itemID = builder.itemID;
        this.orderID = builder.orderID;
        this.product = builder.product;
        this.quantity = builder.quantity;
        this.unitPrice = builder.unitPrice;
        this.subTotal = builder.subTotal;
    }

    public int getItemID() {
        return itemID;
    }

    public int getOrderID() {
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

        private int itemID;
        private int orderID;
        private Product product;
        private int quantity;
        private double unitPrice;
        private double subTotal;

        public Builder setItemID(int itemID) {
            this.itemID = itemID;
            return this;
        }

        public Builder setOrderID(int orderID) {
            this.orderID = orderID;
            return this;
        }

        public Builder setProduct(Product product) {
            this.product = this.product;
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
            return new OrderItem(this);

        }
    }
}








