package za.ac.cput.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import za.ac.cput.domain.Inventory;
import za.ac.cput.domain.Product;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class InventoryControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private final String BASE_URL = "/inventory";

    private Product dummyProduct() {
        return new Product.Builder()
                .setProductID(101L)  // ⚠️ Must exist in DB or ProductRepository
                .setTitle("Test Product")
                .setPrice(99.99)
                .build();
    }

    private Inventory buildInventory(Product product) {
        return new Inventory.Builder()
                .setProduct(product)
                .build();
    }

    @Test
    void testCreateInventory() {
        Inventory inventory = buildInventory(dummyProduct());

        ResponseEntity<Inventory> response = restTemplate.postForEntity(
                BASE_URL + "/create", inventory, Inventory.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void testReadInventory() {
        Inventory inventory = buildInventory(dummyProduct());
        Inventory created = restTemplate.postForEntity(BASE_URL + "/create", inventory, Inventory.class).getBody();

        Inventory readInventory = restTemplate.getForObject(BASE_URL + "/read/" + created.getInventoryID(), Inventory.class);
        assertNotNull(readInventory);
    }

    @Test
    void testUpdateInventory() {
        Inventory inventory = buildInventory(dummyProduct());
        Inventory created = restTemplate.postForEntity(BASE_URL + "/create", inventory, Inventory.class).getBody();

        Inventory updated = new Inventory.Builder()
                .copy(created)
                .build();

        HttpEntity<Inventory> request = new HttpEntity<>(updated);
        ResponseEntity<Inventory> response = restTemplate.exchange(
                BASE_URL + "/update/" + created.getInventoryID(),
                HttpMethod.PUT,
                request,
                Inventory.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testDeleteInventory() {
        Inventory inventory = buildInventory(dummyProduct());
        Inventory created = restTemplate.postForEntity(BASE_URL + "/create", inventory, Inventory.class).getBody();

        restTemplate.delete(BASE_URL + "/delete/" + created.getInventoryID());

        Inventory deleted = restTemplate.getForObject(
                BASE_URL + "/read/" + created.getInventoryID(),
                Inventory.class
        );

        assertNull(deleted); // Controller returns null instead of 404
    }

    @Test
    void testGetAllInventory() {
        Inventory inventory1 = buildInventory(dummyProduct());
        Inventory inventory2 = buildInventory(dummyProduct());

        restTemplate.postForEntity(BASE_URL + "/create", inventory1, Inventory.class);
        restTemplate.postForEntity(BASE_URL + "/create", inventory2, Inventory.class);

        ResponseEntity<Inventory[]> response = restTemplate.getForEntity(BASE_URL + "/getAll", Inventory[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().length >= 2);
    }
}
