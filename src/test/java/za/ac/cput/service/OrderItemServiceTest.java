package za.ac.cput.service;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.ac.cput.domain.Order;
import za.ac.cput.domain.OrderItem;
import za.ac.cput.domain.Product;
import za.ac.cput.factory.OrderItemFactory;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
class OrderItemServiceTest {

    @Autowired
    private IOrderItemService service;

    static Order order = new Order.Builder()
            .setOrderID(123L)
            .build();

    static Product product = new Product.Builder()
            .setProductID(456L)
            .build();

    private final OrderItem orderItem = OrderItemFactory.createOrderItem(order.getOrderID(), product,5,19.99);


    @Test
    void a_create() {
        OrderItem newOrderItem = service.create(orderItem);
        assertNotNull(newOrderItem);
        System.out.println(newOrderItem);
    }

    @Test
    void b_read() {
        OrderItem saved = service.create(orderItem); // now it has a generated ID

        OrderItem read = service.read(orderItem.getItemID());
        assertNotNull(read);
        System.out.println(read);
    }

    @Test
    void c_update() {
        OrderItem newOrderItem = new OrderItem.Builder()
                .copy(orderItem)
                .setQuantity(10)
                .build();
        OrderItem updated = service.update(newOrderItem);
        assertNotNull(updated);
        System.out.println(updated);
    }

    @Test
    void d_getAll() {
        System.out.println(service.getAll());
    }
}
