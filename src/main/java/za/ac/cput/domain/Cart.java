package za.ac.cput.domain;

/*
Cart.java
Cart POJO class
Author: Bekithemba Mrwetyana (222706066)
Date: 01 May 2025
*/

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartID; // put it as a Long

    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private User user;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @Column(name ="cart_Items")
    private List<CartItem> cartItems;

    protected Cart() {
    }

    public Cart(Builder builder) {
        this.cartID = builder.cartID;
        this.user = builder.user;
        this.cartItems = builder.cartItems;
    }

    public Long getCartID() {
        return cartID;
    }

    public User getUserID() {
        return user;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "cartID=" + cartID +
                ", userID=" + user +
                '}';
    }

    public static class Builder {

        private Long cartID;
        private User user;
        private List<CartItem> cartItems;

        public Builder setCartID(Long cartID) {
            this.cartID = cartID;
            return this;
        }

        public Builder setUser(User user) {
            this.user = user;
            return this;
        }

        public Builder setCartItem(List<CartItem> cartItems) {
            this.cartItems = cartItems;
            return this;
        }

        public Builder copy(Cart cart) {
            this.cartID = cart.cartID;
            this.user = cart.user;
            this.cartItems = cart.cartItems;
            return this;
        }

        public Cart build() {
            return new Cart(this);
        }

    }

}

