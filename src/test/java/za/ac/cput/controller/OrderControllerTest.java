package za.ac.cput.controller;
/*
OrderControllerTest.java
Order controller test class
Author: Mpilonhle Zimela Mzimela 230197833
Date: 15 July 2025
*/


import za.ac.cput.domain.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import za.ac.cput.domain.enums.OrderStatus;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrderControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private final String BASE_URL = "/api/orders";;

    @Test
    void testCreateOrder() {
        Order order = new Order.Builder()
                .setOrderAmount(120.0)
                .setTotalAmount(150.0)
                .setOrderDate(LocalDateTime.now())
                .setCartItem(Collections.emptyList())
                //.setPaymentStatus(OrderStatus.PENDING)
                .build();

        ResponseEntity<Order> response = restTemplate.postForEntity(
                BASE_URL + "/create", order, Order.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getOrderID());
    }


    @Test
    void testReadOrder() {
        Order order = new Order.Builder()
                .setCartItem(Collections.emptyList())
                .setTotalAmount(200.0)
                .setOrderDate(LocalDateTime.now())
                .setOrderAmount(150.0)
                //.setPaymentStatus(OrderStatus.PENDING)
                .build();

        ResponseEntity<Order> createResponse = restTemplate.postForEntity(
                BASE_URL + "/create", order, Order.class
        );
        Long generatedId = createResponse.getBody().getOrderID();

        ResponseEntity<Order> response = restTemplate.getForEntity(
                BASE_URL + "/read/" + generatedId, Order.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(generatedId, response.getBody().getOrderID());
    }


    @Test
    void testUpdateOrder() {

        Order order = new Order.Builder()
                .setOrderID(103L)

                .setCartItem(Collections.emptyList())
                .setTotalAmount(250.0)
                .setOrderDate(LocalDateTime.now())
                .setOrderAmount(200.0)
                //.setPaymentStatus(OrderStatus.PENDING)
                .build();

        restTemplate.postForEntity(BASE_URL + "/create", order, Order.class);


        Order updatedOrder = new Order.Builder()
                .copy(order)
                //.setPaymentStatus(OrderStatus.COMPLETED)
                .build();

        HttpEntity<Order> request = new HttpEntity<>(updatedOrder);
        ResponseEntity<Order> response = restTemplate.exchange(
                BASE_URL + "/update/" + order.getOrderID(),
                HttpMethod.PUT,
                request,
                Order.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(OrderStatus.COMPLETED, response.getBody().getPaymentStatus());
    }

    @Test
    void testDeleteOrder() {

        Order order = new Order.Builder()
                .setOrderID(104L)
                .setCartItem(Collections.emptyList())
                .setTotalAmount(300.0)
                .setOrderDate(LocalDateTime.now())
                .setOrderAmount(200.0)
                //.setPaymentStatus(OrderStatus.PENDING)
                .build();

        restTemplate.postForEntity(BASE_URL + "/create", order, Order.class);

        ResponseEntity<Void> deleteResponse = restTemplate.exchange(
                BASE_URL + "/delete/" + order.getOrderID(),
                HttpMethod.DELETE,
                null,
                Void.class
        );

        assertEquals(HttpStatus.OK, deleteResponse.getStatusCode());;


        ResponseEntity<Order> readResponse = restTemplate.getForEntity(
                BASE_URL + "/read/" + order.getOrderID(),
                Order.class
        );
        assertNull(readResponse.getBody());
    }

    @Test
    void testGetAllOrders() {
        ResponseEntity<Order[]> response = restTemplate.getForEntity(
                BASE_URL + "/getAll", Order[].class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }
}