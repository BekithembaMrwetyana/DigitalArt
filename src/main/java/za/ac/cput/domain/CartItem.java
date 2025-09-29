package za.ac.cput.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

/*
CartItem.java
CartItem POJO class
Author: Thandolwethu P Mseleku
Date: 25/05/2025
*/

@Entity
@Table(
        name = "cart_item",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "product_id"}) // enforce uniqueness
)
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartItemID;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private User user;

    @Column(name = "price", nullable = false)
    private double price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cartID")
    private Cart cart;

    @Column(name = "quantity", nullable = false)
    private int quantity = 1;

    protected CartItem() {
    }

    public CartItem(Builder builder) {
        this.cartItemID = builder.cartItemID;
        this.cart = builder.cart;
        this.product = builder.product;
        this.user = builder.user;
        this.quantity = builder.quantity;
        this.price = builder.price;
    }

    public Long getCartItemID() {
        return cartItemID;
    }

    public Cart getCart() {
        return cart;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public User getUser() {
        return user;
    }
    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "cartItemID=" + cartItemID +
                ", product=" + (product != null ? product.getProductID() : null) +
                ", user=" + (user != null ? user.getUserId() : null) +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }

    public static class Builder {
        private Long cartItemID;
        private Cart cart;
        private Product product;
        private int quantity;
        private User user;
        private double price;

        public Builder setCartItemID(Long cartItemID) {
            this.cartItemID = cartItemID;
            return this;
        }

        public Builder setQuantity(int quantity) {
            this.quantity = quantity;
            return this;
        }

        public Builder setProduct(Product product) {
            this.product = product;
            return this;
        }

        public Builder setCart(Cart cart) {
            this.cart = cart;
            return this;
        }
        public Builder setUser(User user) {
            this.user = user;
            return this;
        }
        public Builder setPrice(double price) {
            this.price = price;
            return this;
        }

        public Builder copy(CartItem cartItem) {
            this.cartItemID = cartItem.cartItemID;
            this.cart = cartItem.cart;
            this.product = cartItem.product;
             this.user = cartItem.user;
            this.quantity = cartItem.quantity;
            this.price = cartItem.price;
            return this;

        }

        public CartItem build() {
            return new CartItem(this);
        }
    }
}
