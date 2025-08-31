package za.ac.cput.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import za.ac.cput.domain.Cart;
import za.ac.cput.domain.Order;
import za.ac.cput.domain.enums.OrderStatus;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OrderControllerTest {

    private static Order order;

    @Autowired
    private TestRestTemplate restTemplate;

    private static final String BASE_URL = "http://localhost:8080/ADP_Capstone_Project/orders";

    @BeforeAll
    public static void setup() {
        order = new Order.Builder()
                .setOrderID(null) // Let the service generate ID
                .setOrderAmount(120.0)
                .setTotalAmount(150.0)
                .setOrderDate(LocalDateTime.now())
                .setCartItem(Collections.emptyList())
                .setPaymentStatus(OrderStatus.PENDING)
                .build();
    }

    @Test
    void a_create() {
        String url = BASE_URL + "/create";
        ResponseEntity<Order> postResponse = this.restTemplate.postForEntity(url, order, Order.class);
        assertNotNull(postResponse);
        Order createdOrder = postResponse.getBody();

        assertEquals(order.getOrderID(), createdOrder.getOrderID());
        System.out.println("Created: " + createdOrder);
    }

    @Test
    void b_read() {
        String url = BASE_URL + "/read/" + order.getOrderID();
        ResponseEntity<Order> response = this.restTemplate.getForEntity(url, Order.class);
        assertNotNull(response.getBody());
        assertEquals(order.getOrderID(), response.getBody().getOrderID());
        System.out.println("Read: " + response.getBody());
    }

    @Test
    void c_update() {
        Order updatedOrder = new Order.Builder().copy(order)
                .setPaymentStatus(OrderStatus.SHIPPED)
                .build();

        String url = BASE_URL + "/update";
        this.restTemplate.put(url, updatedOrder);

        ResponseEntity<Order> response = this.restTemplate.postForEntity(url, updatedOrder, Order.class);

        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
        assertNotNull(response.getBody());
        //assertEquals(OrderStatus.SHIPPED, response.getBody().getPaymentStatus());
        System.out.println("Updated: " + response.getBody());
    }

    @Test
    void d_getAll() {
        String url = BASE_URL + "/getAll";
        ResponseEntity<Order[]> response = this.restTemplate.getForEntity(url, Order[].class);
        assertNotNull(response.getBody());
        System.out.println("Get All:");
        for (Order o : response.getBody()) {
            System.out.println(o);
        }
    }

    @Test
    void e_delete() {
        String url = BASE_URL + "/delete/" + order.getOrderID();
        this.restTemplate.delete(url);

        // Optional: check read returns 404
        ResponseEntity<Order> response = this.restTemplate.getForEntity(BASE_URL + "/read/" + order.getOrderID(), Order.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        System.out.println("Deleted:" + "true");
    }
}
