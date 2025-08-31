package za.ac.cput.controller;

import za.ac.cput.domain.Cart;
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
class OrderItemControllerTest {

    private static OrderItem orderItem;

    @Autowired
    private TestRestTemplate restTemplate;

    private static final String BASE_URL = "http://localhost:8080/ADP_Capstone_Project/orderItem";

    @BeforeAll
    public static void setup() {
        orderItem = new OrderItem.Builder()
                .setItemID(null) // Let service generate ID
                .setOrderID(101L)
                .setQuantity(2)
                .setUnitPrice(100.00)
                .calculateSubTotal()
                .build();
    }

    @Test
    @Order(1)
    void a_create() {
        String url = BASE_URL + "/create";
        ResponseEntity<OrderItem> postResponse = this.restTemplate.postForEntity(url, orderItem, OrderItem.class);
        assertNotNull(postResponse);
        OrderItem createdItem = postResponse.getBody();
        assertEquals(orderItem.getQuantity(), createdItem.getItemID());
        System.out.println("Created: " + createdItem);
    }

    @Test
    @Order(2)
    void b_read() {
        String url = BASE_URL + "/read/" + orderItem.getItemID();
        ResponseEntity<OrderItem> response = restTemplate.getForEntity(url, OrderItem.class);
        assertEquals(orderItem.getItemID(), response.getBody().getItemID());
        System.out.println("Read: " + response.getBody());
    }

    @Test
    @Order(3)
    void c_update() {
        OrderItem updatedItem = new OrderItem.Builder()
                .copy(orderItem)
                .setQuantity(3)
                .calculateSubTotal()
                .build();

        String url = BASE_URL + "/update";
        this.restTemplate.put(url, updatedItem);
        ResponseEntity<OrderItem> response = restTemplate.postForEntity(url, updatedItem, OrderItem.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(updatedItem.getQuantity(), response.getBody().getQuantity());
        System.out.println("Updated: " + response.getBody());
    }

    @Test
    @Order(4)
    void getAll() {
        String url = BASE_URL + "/getAll";
        ResponseEntity<OrderItem[]> response = restTemplate.getForEntity(url, OrderItem[].class);
        assertNotNull(response.getBody());
        System.out.println("Get All:");
        for (OrderItem item : response.getBody()) {
            System.out.println(item);
        }
    }

    @Test
    @Order(5)
    void delete() {
        String url = BASE_URL + "/delete/" + orderItem.getItemID();
        this.restTemplate.delete(url);

        // After delete, get should return 404
        ResponseEntity<OrderItem> response = restTemplate.getForEntity(BASE_URL + "/read/" + orderItem.getItemID(), OrderItem.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        System.out.println("Deleted: true");
    }
}


