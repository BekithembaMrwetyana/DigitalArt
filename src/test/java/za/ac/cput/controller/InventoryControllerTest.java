package za.ac.cput.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import za.ac.cput.domain.Inventory;
import za.ac.cput.domain.Product;
import za.ac.cput.factory.InventoryFactory;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class InventoryControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private final String BASE_URL = "/inventory";

    private Product dummyProduct() {
        return new Product.Builder()
                .setProductID(101L)
                .setTitle("Test Product")
                .setPrice(99.99)
                .build();
    }

    private Inventory buildInventory(Product product, int quantity) {
        // Generate a unique Long ID
        return new Inventory.Builder()
                .setInventoryID(System.currentTimeMillis())
                .setProduct(product)
                .setQuantity(quantity)
                .build();
    }

    @Test
    void testCreateInventory() {
        Inventory inventory = buildInventory(dummyProduct(), 50);

        ResponseEntity<Inventory> response = restTemplate.postForEntity(
                BASE_URL + "/create", inventory, Inventory.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(50, response.getBody().getQuantity());
    }

    @Test
    void testReadInventory() {
        Inventory inventory = buildInventory(dummyProduct(), 20);
        Inventory created = restTemplate.postForEntity(BASE_URL + "/create", inventory, Inventory.class).getBody();

        Inventory readInventory = restTemplate.getForObject(BASE_URL + "/read/" + created.getInventoryID(), Inventory.class);
        assertNotNull(readInventory);
        assertEquals(20, readInventory.getQuantity());
    }

    @Test
    void testUpdateInventory() {
        Inventory inventory = buildInventory(dummyProduct(), 10);
        Inventory created = restTemplate.postForEntity(BASE_URL + "/create", inventory, Inventory.class).getBody();

        Inventory updated = new Inventory.Builder()
                .copy(created)
                .setQuantity(25)
                .build();

        HttpEntity<Inventory> request = new HttpEntity<>(updated);
        ResponseEntity<Inventory> response = restTemplate.exchange(
                BASE_URL + "/update",
                HttpMethod.PUT,
                request,
                Inventory.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(25, response.getBody().getQuantity());
    }

    @Test
    void testDeleteInventory() {
        Inventory inventory = buildInventory(dummyProduct(), 15);
        Inventory created = restTemplate.postForEntity(BASE_URL + "/create", inventory, Inventory.class).getBody();

        restTemplate.delete(BASE_URL + "/delete/" + created.getInventoryID());

        ResponseEntity<Inventory> response = restTemplate.getForEntity(
                BASE_URL + "/read/" + created.getInventoryID(),
                Inventory.class
        );

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testGetAllInventory() {
        Inventory inventory1 = buildInventory(dummyProduct(), 5);
        Inventory inventory2 = buildInventory(dummyProduct(), 10);

        restTemplate.postForEntity(BASE_URL + "/create", inventory1, Inventory.class);
        restTemplate.postForEntity(BASE_URL + "/create", inventory2, Inventory.class);

        ResponseEntity<Inventory[]> response = restTemplate.getForEntity(BASE_URL + "/getAll", Inventory[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().length >= 2);
    }
}
