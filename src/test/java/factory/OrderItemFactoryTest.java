package factory;
/*
OrderItemFactoryTest.java
OrderItem Factory Test class
Author: Thimma Gogwana 222213973
Date: 25 May 2025
*/

import domain.OrderItem;
import domain.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import static org.junit.jupiter.api.Assertions.*;

class OrderItemFactoryTest {
    private final OrderItemFactory factory = new OrderItemFactory();
    private final Product testProduct = new Product.Builder()
            .setProductID("prod-001")
            .setTitle("Test Product")
            .setPrice(10.99)
            .build();

    @Test
    void testCreateValidOrderItem() {
        OrderItem item = factory.create(100, testProduct, 2, 10.99);

        assertNotNull(item);
        assertEquals(100, item.getOrderID());
        assertEquals(testProduct, item.getProduct());
        assertEquals(2, item.getQuantity());
        assertEquals(10.99, item.getUnitPrice(), 0.001);
        assertEquals(21.98, item.getSubTotal(), 0.001);
    }

    @Test
    void testCreateWithInvalidProduct() {
        Executable action = () -> factory.create(100, null, 2, 10.99);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, action);
        assertEquals("Invalid Product association", exception.getMessage());
    }

    @Test
    void testUpdateQuantity() {
        OrderItem original = factory.create(100, testProduct, 2, 10.99);
        OrderItem updated = factory.updateQuantity(original, 5);

        assertEquals(5, updated.getQuantity());
        assertEquals(54.95, updated.getSubTotal(), 0.001);
    }
}



