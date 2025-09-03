package za.ac.cput.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.ac.cput.domain.Category;
import za.ac.cput.domain.OrderItem;
import za.ac.cput.domain.Product;
import za.ac.cput.factory.ProductFactory;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class OrderItemServiceTest {

    @Autowired
    private OrderItemService service;

    @Autowired
    private ProductFactory productFactory;

    private Product product;
    private OrderItem orderItem;
    private Category category;

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
                .setOrderID(1L)
                .setProduct(product)
                .setQuantity(3)
                .setUnitPrice(product.getPrice())
                .calculateSubTotal()
                .build();

        orderItem = service.create(orderItem);

        assertNotNull(orderItem.getItemID());
        assertEquals(450.0, orderItem.getSubTotal());
    }

    @Test
    @Order(2)
    void b_read() {
        OrderItem read = service.read(orderItem.getItemID());
        assertNotNull(read);
        assertEquals(orderItem.getQuantity(), read.getQuantity());
        assertEquals("/images/portrait1.jpg", read.getProduct().getImageUrl());
    }

    @Test
    @Order(3)
    void c_update() {
        OrderItem updated = new OrderItem.Builder()
                .copy(orderItem)
                .setQuantity(5)
                .calculateSubTotal()
                .build();

        OrderItem result = service.update(updated);
        assertEquals(750.0, result.getSubTotal());
    }

    @Test
    @Order(4)
    void d_delete() {
        service.delete(orderItem.getItemID());
        assertNull(service.read(orderItem.getItemID()));
    }
}
