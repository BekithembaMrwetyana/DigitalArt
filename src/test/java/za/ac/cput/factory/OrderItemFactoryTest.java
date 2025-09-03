package za.ac.cput.factory;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.ac.cput.domain.Category;
import za.ac.cput.domain.Product;
import za.ac.cput.domain.OrderItem;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OrderItemFactoryTest {

    @Autowired
    private ProductFactory productFactory;

    @Autowired
    private OrderItemFactory orderItemFactory;

    private static Product product;
    private static OrderItem orderItem;
    private static Category category;

    @BeforeAll
    static void setUpCategory() {
        category = new Category.Builder()
                .setCategoryId(101L)
                .setName("Digital Art")
                .setDescription("All digital artworks")
                .build();
    }

    @Test
    @Order(1)
    void a_createProduct() {
<<<<<<< HEAD
        product = productFactory.create(
                1L,
                new Category.Builder()
                        .setCategoryId(101L)
                        .setName("Digital Art")
                        .setDescription("All digital artworks")
                        .build(),
                "Portrait Art",
                "High resolution digital portrait",
                150.0,"Image1"
        );
=======
        product = productFactory.create(1L, category, "Portrait Art", "Digital portrait", 150.0, "portrait1.jpg");
>>>>>>> f4960f5feb3b10bde06d7ae53c265dd2644e9506

        assertNotNull(product);
        assertEquals("/images/portrait1.jpg", product.getImageUrl());
    }

    @Test
    @Order(2)
    void b_createOrderItem() {
        orderItem = orderItemFactory.create(1L, product, 3);

        assertNotNull(orderItem);
        assertEquals(3, orderItem.getQuantity());
        assertEquals(150.0, orderItem.getUnitPrice());
        assertEquals(450.0, orderItem.getSubTotal());
        assertEquals("/images/portrait1.jpg", orderItem.getProduct().getImageUrl());
    }

    @Test
    @Order(3)
    void c_copyOrderItem() {
        OrderItem copy = orderItemFactory.copy(orderItem);

        assertNotNull(copy);
        assertEquals(orderItem.getQuantity(), copy.getQuantity());
        assertEquals(orderItem.getSubTotal(), copy.getSubTotal());
        assertEquals(orderItem.getProduct().getImageUrl(), copy.getProduct().getImageUrl());
    }
}