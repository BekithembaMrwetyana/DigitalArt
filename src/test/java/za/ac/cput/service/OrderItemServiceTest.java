package za.ac.cput.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.ac.cput.domain.Order;
import za.ac.cput.domain.OrderItem;
import za.ac.cput.domain.Product;
import za.ac.cput.factory.OrderItemFactory;

import java.util.List;


import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
class OrderItemServiceTest {

    @Autowired
    private IOrderItemService service;

    @Autowired
    private OrderItemFactory orderItemFactory;

    private static final Order order = new Order.Builder()
            .setOrderID(123L)
            .build();

    private static final Product product = new Product.Builder()
            .setProductID(456L)
            .setTitle("Test Product")
            .setPrice(19.99)
            .build();

    private static OrderItem orderItem;

    @BeforeAll
    static void setup(@Autowired OrderItemFactory factory) {
        // use factory to create an OrderItem
        orderItem = factory.create(order.getOrderID(), product, 5);
    }

    @Test
    void a_create() {
        OrderItem created = service.create(orderItem);
        assertNotNull(created);
        assertEquals(orderItem.getOrderID(), created.getOrderID());
        assertEquals(orderItem.getProduct().getProductID(), created.getProduct().getProductID());
        assertEquals(orderItem.getQuantity(), created.getQuantity());
        assertEquals(orderItem.getSubTotal(), created.getSubTotal());
        System.out.println("Created: " + created);
    }

    @Test
    void b_read() {
        OrderItem read = service.read(orderItem.getItemID());
        assertNotNull(read);
        assertEquals(orderItem.getItemID(), read.getItemID());
        System.out.println("Read: " + read);
    }

    @Test
    void c_update() {
        OrderItem updatedItem = new OrderItem.Builder()
                .copy(orderItem)
                .setQuantity(10)
                .calculateSubTotal()
                .build();

        OrderItem updated = service.update(updatedItem);
        assertNotNull(updated);
        assertEquals(10, updated.getQuantity());
        assertEquals(orderItem.getItemID(), updated.getItemID());
        assertEquals(10 * product.getPrice(), updated.getSubTotal());
        System.out.println(" Updated: " + updated);
    }

    @Test
    void d_getAll() {
        List<OrderItem> all = service.getAll();
        assertNotNull(all);
        assertFalse(all.isEmpty(), "Expected service to contain at least one OrderItem");
        assertTrue(all.stream().anyMatch(i -> i.getItemID().equals(orderItem.getItemID())));
        System.out.println(" GetAll: " + all);
    }

    @Test
    void e_delete() {
        service.delete(orderItem.getItemID());
        OrderItem deletedItem = service.read(orderItem.getItemID());
        assertNull(deletedItem, "Deleted OrderItem should not be found");
        System.out.println(" Deleted OrderItem with ID: " + orderItem.getItemID());
    }
}
