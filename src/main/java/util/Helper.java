package util;

import domain.Order;
import domain.OrderItem;
import domain.Payment;
import domain.Product;

import java.time.LocalDate;
import java.util.UUID;

public class Helper {
    public static boolean isValidCartItemID(int cartItemID) {
        return cartItemID >= 0;
    }

    public static boolean isValidCartID(int cartID) {
        return cartID >= 0;
    }

    public static boolean isValidProductID(int productID) {
        return productID >0;
    }

    public static boolean isValidQuantity(int quantity) {
        return quantity >0;
    }
    public static boolean isValidOrderItemID(int orderItemID) {
        return orderItemID > 0; // Typically starts from 1
    }

    public static boolean isValidOrderID(int orderID) {
        return orderID > 0; // Typically starts from 1
    }

    public static boolean isValidUnitPrice(double unitPrice) {
        return unitPrice >= 0.0 && unitPrice <= 999999.99; // Reasonable price range
    }

    public static boolean isValidSubTotal(double subTotal) {
        return subTotal >= 0.0 && subTotal <= 9999999.99; // Higher limit for summed values
    }

    public static boolean isValidOrderItem(OrderItem orderItem) {
        return orderItem != null &&
                isValidOrderItemID(orderItem.getItemID()) &&
                isValidOrderID(orderItem.getOrderID()) &&
                isValidProduct(orderItem.getProduct()) &&
                isValidQuantity(orderItem.getQuantity()) &&
                isValidUnitPrice(orderItem.getUnitPrice()) &&
                isValidSubTotal(orderItem.getSubTotal()) &&
                Math.abs(orderItem.getSubTotal() - (orderItem.getUnitPrice() * orderItem.getQuantity())) < 0.001; // Verify calculation
    }

    public static void validateOrderItem(OrderItem orderItem) {
        if (!isValidOrderItem(orderItem)) {
            throw new IllegalArgumentException("Invalid OrderItem: " + orderItem);
        }
    }
    public static boolean isValidProductID(String productID) {
        return productID != null && !productID.trim().isEmpty() && productID.matches("prod-\\d+");
    }

    public static boolean isValidTitle(String title) {
        return title != null && !title.trim().isEmpty() && title.length() <= 100;
    }

    public static boolean isValidDescription(String description) {
        return description != null && !description.trim().isEmpty() && description.length() <= 500;
    }

    public static boolean isValidPrice(double price) {
        return price >= 0.0 && price <= 999999.99;
    }

    public static boolean isValidCategoryID(String categoryID) {
        return categoryID != null && !categoryID.trim().isEmpty() && categoryID.matches("cat-\\d+");
    }

    public static boolean isValidProduct(Product product) {
        return product != null &&
                isValidProductID(product.getProductID()) &&
                isValidTitle(product.getTitle()) &&
                (product.getDescription() == null || isValidDescription(product.getDescription())) &&
                isValidPrice(product.getPrice()) &&
                (product.getCategoryID() == null || isValidCategoryID(product.getCategoryID()));
    }
     public static String generateId() {
        return UUID.randomUUID().toString();
     }

     public static boolean isNullOrEmpty(String str) {
        if (str.isEmpty() || str == null)
            return true;
        return false;
     }
    public static boolean isValidEntityId(String id) {
        return id != null && !id.trim().isEmpty() && id.length() <= 50;
    }

    public static boolean isValidEntityId(int id) {
        return id > 0;
    }

    public static boolean isValidProductAssociation(Product product) {
        return product != null && isValidEntityId(product.getProductID());
    }

    public static boolean isValidOrderAssociation(Order order) {
        return order != null && isValidEntityId(order.getOrderID());
    }

    // Payment validations
    public static boolean isValidPaymentID(String paymentID) {
        return paymentID != null && !paymentID.trim().isEmpty() && paymentID.matches("pay-\\d+");
    }
    public static boolean isValidPaymentDate(LocalDate paymentDate) {
        return paymentDate != null && !paymentDate.isAfter(LocalDate.now());
    }
    public static boolean isValidAmount(double amount) {
        return amount >= 0.0 && amount <= 999999.99;
    }
    public static boolean isValidStatus(String status) {
        return status != null && !status.trim().isEmpty() && (status.equalsIgnoreCase("Pending") || status.equalsIgnoreCase("Completed") || status.equalsIgnoreCase("Failed"));
    }
    public static boolean isValidMethod(String method) {
        return method != null && !method.trim().isEmpty() && (method.equalsIgnoreCase("Credit Card") || method.equalsIgnoreCase("Debit Card"));
    }
    public static boolean isValidPayment(Payment payment) {
        return payment != null &&
                isValidPaymentID(payment.getPaymentID()) &&
                isValidPaymentDate(payment.getPaymentDate()) &&
                isValidAmount(payment.getAmount()) &&
                isValidStatus(payment.getStatus()) &&
                isValidMethod(payment.getMethod());
    }

    // Cart validations

    public static boolean isValidCartID(String cartID) {
        return cartID != null && !cartID.trim().isEmpty() && cartID.matches("cart-\\d+");
    }
    public static boolean isValidUser(int userID) {
        return userID > 0;
    }


}
