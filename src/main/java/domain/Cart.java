package domain;

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
    private String cartID;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userID", referencedColumnName = "userID")
    private User userID;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    private List<CartItem> cartItems;

    protected Cart(){}

    public Cart(Builder builder) {
        this.cartID = builder.cartID;
        this.userID = builder.userID;
        this.cartItems = builder.cartItems;
    }

    public String getCartID() {
        return cartID;
    }

    public User getUserID() {
        return userID;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "cartID=" + cartID +
                ", userID=" + userID +
                ", cartItem=" + cartItems +
                '}';
    }

    public static class Builder{

        private String cartID;
        private User userID;
        private List<CartItem> cartItems;

        public Builder setCartID(String cartID) {
            this.cartID = cartID;
            return this;
        }

        public Builder setUserID(User userID) {
            this.userID = userID;
            return this;
        }

        public Builder setCartItem(List<CartItem> cartItems) {
            this.cartItems = cartItems;
            return this;
        }

        public Builder copy(Cart cart){
            this.cartID = cart.cartID;
            this.userID = cart.userID;
            this.cartItems = cart.cartItems;
            return this;
        }
        public Cart build(){
            return new Cart(this);
        }

    }

}

