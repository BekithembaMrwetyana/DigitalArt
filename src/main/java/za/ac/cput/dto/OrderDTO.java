//package za.ac.cput.dto;
//
//import java.util.List;
//
//public class OrderDTO {
//
//    private Long userId;
//    private List<CartItemDTO> cartItems;
//    private double totalAmount;
//    private double orderAmount;
//
//    // Getters and setters
//    public Long getUserId() {
//        return userId;
//    }
//    public void setUserId(Long userId) {
//        this.userId = userId;
//    }
//    public List<CartItemDTO> getCartItems() {
//        return cartItems;
//    }
//    public void setCartItems(List<CartItemDTO> cartItems) {
//        this.cartItems = cartItems;
//    }
//    public double getTotalAmount() {
//        return totalAmount;
//    }
//    public void setTotalAmount(double totalAmount) {
//        this.totalAmount = totalAmount;
//    }
//    public double getOrderAmount() {
//        return orderAmount;
//    }
//    public void setOrderAmount(double orderAmount) {
//        this.orderAmount = orderAmount;
//    }
//}
package za.ac.cput.dto;

import java.time.LocalDateTime;
import java.util.List;

/**
 * OrderDTO
 * Data Transfer Object for Orders in the Digital Art eCommerce system.
 * Includes user, cart items, totals, and generated download links after order completion.
 *
 * Author: Mpilo Mzimela
 * Date: 06 October 2025
 */
public class OrderDTO {

    private Long orderId;
    private Long userId;
    private List<CartItemDTO> cartItems;
    private double totalAmount;
    private double orderAmount;
    private LocalDateTime orderDate;

    // ✅ New field — generated download links for digital art
    private List<String> downloadLinks;

    // Getters and setters
    public Long getOrderId() {
        return orderId;
    }
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

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

    public LocalDateTime getOrderDate() {
        return orderDate;
    }
    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public List<String> getDownloadLinks() {
        return downloadLinks;
    }
    public void setDownloadLinks(List<String> downloadLinks) {
        this.downloadLinks = downloadLinks;
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
                "orderId=" + orderId +
                ", userId=" + userId +
                ", totalAmount=" + totalAmount +
                ", orderAmount=" + orderAmount +
                ", orderDate=" + orderDate +
                ", downloadLinks=" + downloadLinks +
                '}';
    }
}
