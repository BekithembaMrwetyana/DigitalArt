package factory;
/*
OrderFactoryTest.java
Order Factory Test class
Author: Mpilonhle Zimela Mzimela 230197833
Date: 14 May 2025
*/
import domain.Order;
import domain.OrderItem;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import domain.Order;
import domain.OrderItem;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderFactoryTest {

    @Test
    void testCreateOrder() {

        int orderID = 1;
        int userID = 100;


        OrderItem item1 = new OrderItem.Builder()
                .setItemID(1)
                .setOrderID(orderID)

                .setQuantity(2)
                .setUnitPrice(10.99)
                .calculateSubTotal()
                .build();

        OrderItem item2 = new OrderItem.Builder()
                .setItemID(2)
                .setOrderID(orderID)

                .setUnitPrice(5.99)
                .calculateSubTotal()
                .build();

        List<OrderItem> orderItems = List.of(item1, item2);
        double totalAmount = item1.getSubTotal() + item2.getSubTotal();
        LocalDateTime orderDate = LocalDateTime.now();
        int paymentID = 500;
        String paymentStatus = "PAID";


        Order order = OrderFactory.createOrder(
                orderID,
                userID,
                orderItems,
                totalAmount,
                orderDate,
                paymentID,
                paymentStatus
        );


        assertNotNull(order);
        assertEquals(orderID, order.getOrderID());
        assertEquals(userID, order.getUserID());
        assertEquals(2, order.getOrderItems().size());
        assertEquals(totalAmount, order.getTotalAmount(), 0.001);
        assertEquals(orderDate, order.getOrderDate());
        assertEquals(paymentID, order.getPaymentID());
        assertEquals(paymentStatus, order.getPaymentStatus());


        OrderItem firstItem = order.getOrderItems().get(0);
        assertEquals(1, firstItem.getItemID());
        assertEquals(orderID, firstItem.getOrderID());

        assertEquals(2, firstItem.getQuantity());
        assertEquals(10.99, firstItem.getUnitPrice(), 0.001);
        assertEquals(21.98, firstItem.getSubTotal(), 0.001);
    }

    @Test
    void testCreateOrderWithSingleItem() {

        int orderID = 2;
        int userID = 101;

        OrderItem item = new OrderItem.Builder()
                .setItemID(3)
                .setOrderID(orderID)

                .setQuantity(1)
                .setUnitPrice(15.50)
                .calculateSubTotal()
                .build();

        List<OrderItem> orderItems = List.of(item);
        double totalAmount = item.getSubTotal();
        LocalDateTime orderDate = LocalDateTime.of(2025, 5, 15, 10, 30);
        int paymentID = 501;
        String paymentStatus = "PENDING";

        Order order = OrderFactory.createOrder(
                orderID,
                userID,
                orderItems,
                totalAmount,
                orderDate,
                paymentID,
                paymentStatus
        );


        assertNotNull(order);
        assertEquals(orderID, order.getOrderID());
        assertEquals(userID, order.getUserID());
        assertEquals(1, order.getOrderItems().size());
        assertEquals(totalAmount, order.getTotalAmount(), 0.001);
        assertEquals(orderDate, order.getOrderDate());
        assertEquals(paymentID, order.getPaymentID());
        assertEquals(paymentStatus, order.getPaymentStatus());


        OrderItem orderItem = order.getOrderItems().get(0);
        assertEquals(3, orderItem.getItemID());
        assertEquals(orderID, orderItem.getOrderID());

        assertEquals(1, orderItem.getQuantity());
        assertEquals(15.50, orderItem.getUnitPrice(), 0.001);
        assertEquals(15.50, orderItem.getSubTotal(), 0.001);
    }

    @Test
    void testCreateOrderWithEmptyItems() {
        int orderID = 3;
        int userID = 102;
        List<OrderItem> orderItems = List.of();
        double totalAmount = 0.0;
        LocalDateTime orderDate = LocalDateTime.now();
        int paymentID = 502;
        String paymentStatus = "FAILED";


        Order order = OrderFactory.createOrder(
                orderID,
                userID,
                orderItems,
                totalAmount,
                orderDate,
                paymentID,
                paymentStatus
        );


        assertNotNull(order);
        assertEquals(orderID, order.getOrderID());
        assertEquals(userID, order.getUserID());
        assertTrue(order.getOrderItems().isEmpty());
        assertEquals(totalAmount, order.getTotalAmount(), 0.001);
        assertEquals(orderDate, order.getOrderDate());
        assertEquals(paymentID, order.getPaymentID());
        assertEquals(paymentStatus, order.getPaymentStatus());
    }
}