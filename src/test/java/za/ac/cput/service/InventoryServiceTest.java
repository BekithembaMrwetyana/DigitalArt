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

    private Product dummyProduct() {
        return new Product.Builder()
                .setProductID(1L)
                .setTitle("Test Product")
                .setPrice(99.99)
                .build();
    }

    private Inventory buildInventory(Long id, Product product) {
        return new Inventory.Builder()
                .setInventoryID(id)
                .setProduct(product)
                .build();
    }

    @BeforeEach
    void setUp() {
        repository.deleteAll();
        testInventory = buildInventory(1L, dummyProduct());
        service.create(testInventory);
    }

    @Test
    void a_testCreate() {
        assertNotNull(testInventory.getInventoryID());
    }

    @Test
    void b_testRead() {
        Inventory found = service.read(testInventory.getInventoryID());
        assertNotNull(found);
    }

    @Test
    void c_testUpdate() {
        Inventory updatedInventory = new Inventory.Builder()
                .copy(testInventory)
                .build();

        Inventory result = service.update(updatedInventory);
        assertNotNull(result);
    }

    @Test
    void d_testGetAll() {
        List<Inventory> inventories = service.getAll();
        assertNotNull(inventories);
        assertFalse(inventories.isEmpty());
        assertTrue(inventories.stream()
                .anyMatch(inv -> inv.getInventoryID().equals(testInventory.getInventoryID())));
    }

    @Test
    void e_testDelete() {
        service.delete(testInventory.getInventoryID());
        Inventory deleted = service.read(testInventory.getInventoryID());
        assertNull(deleted);
    }
}
