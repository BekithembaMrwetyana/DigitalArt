package za.ac.cput.dto;

import java.util.List;

public class OrderDTO {

    private Long userId;
    private List<CartItemDTO> cartItems;
    private double totalAmount;
    private double orderAmount;

    // Getters and setters
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public List<CartItemDTO> getCartItems() {
        return cartItems;
    }
    public void setCartItems(List<CartItemDTO> cartItems) {
        this.cartItems = cartItems;
    }
    public double getTotalAmount() {
        return totalAmount;
    }
    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
    public double getOrderAmount() {
        return orderAmount;
    }
    public void setOrderAmount(double orderAmount) {
        this.orderAmount = orderAmount;
    }
}
