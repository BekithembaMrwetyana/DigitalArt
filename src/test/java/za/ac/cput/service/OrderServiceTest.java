package za.ac.cput.service;

/*
OrderServiceTest.java
Order Service test class
Author: Mpilonhle Zimela Mzimela 230197833
Date: 20 July 2025
*/

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import za.ac.cput.domain.Order;
import za.ac.cput.domain.User;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.ac.cput.domain.enums.OrderStatus;
import za.ac.cput.repository.OrderRepository;
import za.ac.cput.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
public class OrderServiceTest {

    @Autowired
    private OrderService service;

    @Autowired
    private OrderRepository repository;

    @Autowired
    private UserRepository userRepository;

    private Order testOrder;

    @BeforeEach
    void setUp() {
        repository.deleteAll();
        userRepository.deleteAll();

        User testUser = new User.Builder()
                .setFirstName("Test")
                .setLastName("User")
                //.setEmail("testuser@example.com")
                .setPassword("password123")
                .build();
        testUser = userRepository.save(testUser);

        testOrder = new Order.Builder()
                .setUser(testUser)
                .setCartItem(Collections.emptyList())
                .setTotalAmount(200.00)
                .setOrderAmount(150.00)
                .setOrderDate(LocalDateTime.now())
                .setPaymentStatus(OrderStatus.PENDING)
                .build();


    }

    @Test
    void a_testCreate() {
        testOrder = service.create(testOrder);
        assertNotNull(testOrder);
        assertNotNull(testOrder.getOrderID(), "Order ID must be auto-generated");
        assertEquals(OrderStatus.PENDING, testOrder.getPaymentStatus());
    }

    @Test
    void b_testRead() {
        Order found = service.read(testOrder.getOrderID());
        assertNotNull(found);
        assertEquals(testOrder.getOrderID(), found.getOrderID());
    }

    @Test
    void c_testUpdate() {
        Order updated = new Order.Builder()
                .copy(testOrder)
                .setPaymentStatus(OrderStatus.SHIPPED)
                .build();

        Order result = service.update(updated);
        assertEquals(OrderStatus.SHIPPED, result.getPaymentStatus());
    }

    @Test
    void d_testGetAll() {
        List<Order> orders = service.getAll();
        assertNotNull(orders);
        assertFalse(orders.isEmpty());
        System.out.println("Orders: " + orders);
    }
}

