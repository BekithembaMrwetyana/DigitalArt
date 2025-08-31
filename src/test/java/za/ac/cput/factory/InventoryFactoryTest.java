package za.ac.cput.factory;

import org.junit.jupiter.api.Test;
import za.ac.cput.domain.Inventory;
import za.ac.cput.domain.Product;

import static org.junit.jupiter.api.Assertions.*;

class InventoryFactoryTest {

    private Product dummyProduct() {
        return new Product.Builder()
                .setProductID(1L)
                .setTitle("Test Product")
                .setPrice(99.99)
                .build();
    }

    @Test
    void testCreateInventory() {
        Product product = dummyProduct();
        int quantity = 50;

        Inventory inventory = new Inventory.Builder()
                .setInventoryID(25L)
                .setProduct(product)
                .setQuantity(quantity)
                .build();

        assertNotNull(inventory);
        assertEquals("inv-001", inventory.getInventoryID());
        assertEquals(product, inventory.getProduct());
        assertEquals(quantity, inventory.getQuantity());
    }

    @Test
    void testCreateInventoryWithInvalidProduct() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Inventory.Builder()
                    .setInventoryID(20L)
                    .setProduct(null)
                    .setQuantity(10)
                    .build();
        });
        assertEquals("Product cannot be null", exception.getMessage());
    }

    @Test
    void testCreateInventoryWithInvalidQuantity() {
        Product product = dummyProduct();

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Inventory.Builder()
                    .setInventoryID(26L)
                    .setProduct(product)
                    .setQuantity(-5)
                    .build();
        });
        assertEquals("Quantity cannot be negative", exception.getMessage());
    }
}
