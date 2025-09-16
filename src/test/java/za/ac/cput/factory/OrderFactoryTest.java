package za.ac.cput.factory;

import org.junit.jupiter.api.Test;
import za.ac.cput.domain.CartItem;
import za.ac.cput.domain.Order;
import za.ac.cput.domain.Product;
import za.ac.cput.domain.enums.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderFactoryTest {

    @Test
    void testCreateOrder() {
        Long orderID = 1L;
        double orderAmount = 100.0;


        Product product1 = new Product.Builder()
                .setProductID(1L)
                .setTitle("Art2")
                .setPrice(10.99)
                .build();

        Product product2 = new Product.Builder()
                .setProductID(1L)
                .setTitle("Mouse")
                .setPrice(5.99)
                .build();


        CartItem item1 = new CartItem.Builder()
                .setCartItemID(1L)
                .setProduct(product1)
                .setQuantity(2)
                .build();

        CartItem item2 = new CartItem.Builder()
                .setCartItemID(2L)
                .setProduct(product2)
                .setQuantity(1)
                .build();

        List<CartItem> cartItems = List.of(item1, item2);
        double totalAmount = (item1.getProduct().getPrice() * item1.getQuantity())
                + (item2.getProduct().getPrice() * item2.getQuantity());

        LocalDateTime orderDate = LocalDateTime.now();
        OrderStatus paymentStatus = OrderStatus.COMPLETED;

        Order order = OrderFactory.createOrder(
                orderID,
                cartItems,
                totalAmount,
                orderDate,
                orderAmount,
                paymentStatus
        );

        assertNotNull(order);
        assertEquals(orderID, order.getOrderID());
        assertEquals(orderAmount, order.getOrderAmount(), 0.001);
        assertEquals(2, order.getCartItems().size());
        assertEquals(totalAmount, order.getTotalAmount(), 0.001);
        assertEquals(orderDate, order.getOrderDate());
        assertEquals(paymentStatus, order.getPaymentStatus());

        CartItem firstItem = order.getCartItems().get(0);
        assertEquals(1, firstItem.getCartItemID());
        assertEquals(2, firstItem.getQuantity());
        assertEquals(product1, firstItem.getProduct());
    }

    @Test
    void testCreateOrderWithSingleItem() {
        Long orderID = 2L;
        double orderAmount = 50.0;

        Product product = new Product.Builder()
                .setProductID(3L)
                .setTitle("ART1")
                .setPrice(15.50)
                .build();

        CartItem item = new CartItem.Builder()
                .setCartItemID(3L)
                .setProduct(product)
                .setQuantity(1)
                .build();

        List<CartItem> cartItems = List.of(item);
        double totalAmount = item.getProduct().getPrice() * item.getQuantity();

        LocalDateTime orderDate = LocalDateTime.of(2025, 5, 15, 10, 30);
        OrderStatus paymentStatus = OrderStatus.PENDING;

        Order order = OrderFactory.createOrder(
                orderID,
                cartItems,
                totalAmount,
                orderDate,
                orderAmount,
                paymentStatus
        );

        assertNotNull(order);
        assertEquals(orderID, order.getOrderID());
        assertEquals(orderAmount, order.getOrderAmount(), 0.001);
        assertEquals(1, order.getCartItems().size());
        assertEquals(totalAmount, order.getTotalAmount(), 0.001);
        assertEquals(orderDate, order.getOrderDate());
        assertEquals(paymentStatus, order.getPaymentStatus());

        CartItem orderItem = order.getCartItems().get(0);
        assertEquals(3, orderItem.getCartItemID());
        assertEquals(1, orderItem.getQuantity());
        assertEquals(product, orderItem.getProduct());
    }

    @Test
    void testCreateOrderWithEmptyItems() {
        Long orderID = 3L;
        double orderAmount = 0.0;

        List<CartItem> cartItems = List.of();
        double totalAmount = 0.0;
        LocalDateTime orderDate = LocalDateTime.now();
        OrderStatus paymentStatus = OrderStatus.CANCELLED;

        Order order = OrderFactory.createOrder(
                orderID,
                cartItems,
                totalAmount,
                orderDate,
                orderAmount,
                paymentStatus
        );

        assertNotNull(order);
        assertEquals(orderID, order.getOrderID());
        assertEquals(orderAmount, order.getOrderAmount(), 0.001);
        assertTrue(order.getCartItems().isEmpty());
        assertEquals(totalAmount, order.getTotalAmount(), 0.001);
        assertEquals(orderDate, order.getOrderDate());
        assertEquals(paymentStatus, order.getPaymentStatus());
    }
}
