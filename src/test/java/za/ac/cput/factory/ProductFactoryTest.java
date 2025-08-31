package za.ac.cput.factory;

/*
ProductFactoryTest.java
Product Factory Test class
Author: Thimna Gogwana 222213973
Date: 25 May 2025
*/

import za.ac.cput.domain.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import za.ac.cput.factory.ProductFactory;

import static org.junit.jupiter.api.Assertions.*;

class ProductFactoryTest {
    private final ProductFactory factory = new ProductFactory();

    @Test
    void testCreateBasicProduct() {
        Product product = factory.createBasicProduct("Test Product", 10.99);

        assertNotNull(product);
        assertEquals("Test Product", product.getTitle());
        assertEquals(10.99, product.getPrice());
        assertNull(product.getDescription());
        assertNull(product.getCategoryID());
    }

    @Test
    void testCreateFullProduct() {
        Product product = factory.createFullProduct("Test Product", "Test Product", 15, "cat-001");

        assertEquals("Test Description", product.getDescription());
        assertEquals("cat-001", product.getCategoryID());
    }

    @Test
    void testCreateWithInvalidPrice() {
        Executable action = () -> factory.createBasicProduct("Test", -1.0);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, action);
        assertEquals("Invalid price: -1.0", exception.getMessage());
    }
}