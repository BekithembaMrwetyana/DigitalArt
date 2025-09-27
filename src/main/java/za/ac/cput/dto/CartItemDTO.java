package za.ac.cput.dto;

import za.ac.cput.domain.Product;

public class CartItemDTO {
    private Long cartItemID;
    private Product product;   // full product object
    private double price;
    private Long userId;

    public CartItemDTO(Long cartItemID, Product product, double price, Long userId) {
        this.cartItemID = cartItemID;
        this.product = product;
        this.price = price;
        this.userId = userId;
    }

    public Long getCartItemID() { return cartItemID; }
    public Product getProduct() { return product; }
    public double getPrice() { return price; }
    public Long getUserId() { return userId; }
}
