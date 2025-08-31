package za.ac.cput.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.ac.cput.domain.Inventory;
import za.ac.cput.domain.Product;
import za.ac.cput.repository.InventoryRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
public class InventoryServiceTest {

    @Autowired
    private InventoryService service;

    @Autowired
    private InventoryRepository repository;

    private Inventory testInventory;

    // Dummy product for testing
    private Product dummyProduct() {
        return new Product.Builder()
                .setTitle("Test Product")
                .setPrice(99.99)
                .build();
    }

    // Build inventory without setting ID manually
    private Inventory buildInventory(Product product, int quantity) {
        return new Inventory.Builder()
                .setProduct(product)
                .setQuantity(quantity)
                .build();
    }

    @BeforeEach
    void setUp() {
        // Clean DB before each test
        repository.deleteAll();

        // Create inventory via service to ensure ID is generated
        testInventory = service.create(buildInventory(dummyProduct(), 50));
    }

    @Test
    void a_testCreate() {
        assertNotNull(testInventory.getInventoryID());
        assertEquals(50, testInventory.getQuantity());
    }

    @Test
    void b_testRead() {
        Inventory found = service.read(testInventory.getInventoryID());
        assertNotNull(found);
        assertEquals(testInventory.getQuantity(), found.getQuantity());
    }

    @Test
    void c_testUpdate() {
        Inventory updatedInventory = new Inventory.Builder()
                .copy(testInventory)
                .setQuantity(75)
                .build();

        Inventory result = service.update(updatedInventory);
        assertEquals(75, result.getQuantity());
    }

    @Test
    void d_testGetAll() {
        List<Inventory> inventories = service.getAll();
        assertNotNull(inventories);
        assertFalse(inventories.isEmpty());
        assertTrue(inventories.stream().anyMatch(inv -> inv.getInventoryID().equals(testInventory.getInventoryID())));
    }

    @Test
    void e_testDelete() {
        service.delete(testInventory.getInventoryID());
        Inventory deleted = service.read(testInventory.getInventoryID());
        assertNull(deleted);
    }
}
