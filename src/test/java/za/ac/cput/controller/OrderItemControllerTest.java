package za.ac.cput.controller;

import za.ac.cput.domain.OrderItem;
import za.ac.cput.domain.Product;
import za.ac.cput.domain.Category;
import za.ac.cput.factory.ProductFactory;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class OrderItemControllerTest {

    private OrderItem orderItem;
    private Product product;
    private Category category;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ProductFactory productFactory;

    private final String BASE_URL = "/order_items";

    @BeforeAll
    void setup() {
        category = new Category.Builder()
                .setCategoryId(101L)
                .setName("Digital Art")
                .setDescription("All digital artworks")
                .build();

        product = productFactory.create(
                1L,
                category,
                "Portrait Art",
                "Digital portrait",
                150.0,
                "portrait1.jpg"
        );
    }

    @Test
    @Order(1)
    void a_create() {
        orderItem = new OrderItem.Builder()
                .setOrderID(101L)
                .setProduct(product)
                .setQuantity(2)
                .setUnitPrice(product.getPrice())
                .calculateSubTotal()
                .build();

        ResponseEntity<OrderItem> response = restTemplate.postForEntity(
                BASE_URL + "/create",
                orderItem,
                OrderItem.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        orderItem = response.getBody(); // update with DB-generated itemID
        assertNotNull(orderItem.getItemID());
        assertEquals(2, orderItem.getQuantity());
    }

    @Test
    @Order(2)
    void b_read() {
        ResponseEntity<OrderItem> response = restTemplate.getForEntity(
                BASE_URL + "/read/" + orderItem.getItemID(),
                OrderItem.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(orderItem.getItemID(), response.getBody().getItemID());
    }

    @Test
    @Order(3)
    void c_update() {
        OrderItem updatedItem = new OrderItem.Builder()
                .copy(orderItem)
                .setQuantity(3)
                .calculateSubTotal()
                .build();

        HttpEntity<OrderItem> entity = new HttpEntity<>(updatedItem);

        ResponseEntity<OrderItem> response = restTemplate.exchange(
                BASE_URL + "/update",
                HttpMethod.PUT,
                entity,
                OrderItem.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(3, Objects.requireNonNull(response.getBody()).getQuantity());
        orderItem = response.getBody(); // update local reference
    }

    @Test
    @Order(4)
    void d_getAll() {
        ResponseEntity<OrderItem[]> response = restTemplate.getForEntity(
                BASE_URL + "/getAll",
                OrderItem[].class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().length > 0);
    }

    @Test
    @Order(5)
    void e_delete() {
        restTemplate.delete(BASE_URL + "/delete/" + orderItem.getItemID());

        ResponseEntity<OrderItem> response = restTemplate.getForEntity(
                BASE_URL + "/read/" + orderItem.getItemID(),
                OrderItem.class
        );

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
