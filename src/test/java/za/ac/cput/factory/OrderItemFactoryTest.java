package za.ac.cput.factory;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
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

    @Test
    @Order(1)
    void a_createProduct() {
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

        assertNotNull(product);
        assertEquals("Portrait Art", product.getTitle());
        assertEquals(150.0, product.getPrice());
        assertEquals(101L, product.getCategory().getCategoryId());
        System.out.println("Created product: " + product);
    }

    @Test
    @Order(2)
    void b_createOrderItem() {
        orderItem = orderItemFactory.create(1L, product, 3);

        assertNotNull(orderItem);
        assertEquals(3, orderItem.getQuantity());
        assertEquals(product.getProductID(), orderItem.getProduct().getProductID());
        System.out.println("Created order item: " + orderItem);
    }

    @Test
    @Order(3)
    void c_copyOrderItem() {
        OrderItem copy = orderItemFactory.copy(orderItem);

        assertNotNull(copy);
        assertEquals(orderItem.getQuantity(), copy.getQuantity());
        assertEquals(orderItem.getProduct().getTitle(), copy.getProduct().getTitle());
        System.out.println("Copied order item: " + copy);
    }
}
