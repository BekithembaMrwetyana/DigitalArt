package za.ac.cput.controller;

import za.ac.cput.domain.OrderItem;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OrderItemControllerTest {

    private static OrderItem orderItem;

    @Autowired
    private TestRestTemplate restTemplate;

    private final String BASE_URL = "http://localhost:8080/orderitem";


    @Test
    @Order(1)
    void create() {
        orderItem = new OrderItem.Builder()
                .setItemID(1L)
                .setOrderID(101L)
                .setQuantity(2)
                .setUnitPrice(100.00)
                .calculateSubTotal()
                .build();
        ResponseEntity<OrderItem> response = restTemplate.postForEntity(
                BASE_URL + "/create", orderItem, OrderItem.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(orderItem.getItemID(), Objects.requireNonNull(response.getBody()).getItemID());
    }

    @Test
    @Order(2)
    void read() {
        String url = BASE_URL + "/get/" + orderItem.getItemID();
        ResponseEntity<OrderItem> response = restTemplate.getForEntity(url, OrderItem.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(orderItem.getItemID(), response.getBody().getItemID());
    }

    @Test
    @Order(3)
    void update() {
        OrderItem updatedItem = new OrderItem.Builder()
                .copy(orderItem)
                .setQuantity(3)
                .calculateSubTotal()
                .build();

        HttpEntity<OrderItem> entity = new HttpEntity<>(updatedItem);
        ResponseEntity<OrderItem> response = restTemplate.exchange(
                BASE_URL + "/update/" + updatedItem.getItemID(),
                HttpMethod.PUT,
                entity,
                OrderItem.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(3, Objects.requireNonNull(response.getBody()).getQuantity());
    }

    @Test
    @Order(4)
    void delete() {
        String url = BASE_URL + "/delete/" + orderItem.getItemID();
        restTemplate.delete(url);

        ResponseEntity<OrderItem> response = restTemplate.getForEntity(
                BASE_URL + "/get/" + orderItem.getItemID(),
                OrderItem.class
        );

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    @Order(5)
    void getAll() {
        ResponseEntity<OrderItem[]> response = restTemplate.getForEntity(
                BASE_URL + "/getAll", OrderItem[].class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }
}

