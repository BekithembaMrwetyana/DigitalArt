package za.ac.cput.factory;

/*
ProductFactoryTest.java
Product Factory Test class
Author: Thimna Gogwana 222213973
Date: 25 May 2025
*/

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.ac.cput.domain.Category;
import za.ac.cput.domain.Product;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductFactoryTest {

    @Autowired
    private ProductFactory productFactory;

    private static Product product1;
    private static Product product2;

    private static Category category1;
    private static Category category2;

    @BeforeAll
    static void setUpCategories() {
        category1 = new Category.Builder()
                .setCategoryId(1001L)
                .setName("Portraits")
                .setDescription("Portrait artworks")
                .build();

        category2 = new Category.Builder()
                .setCategoryId(1002L)
                .setName("Abstract")
                .setDescription("Abstract artworks")
                .build();
    }

    @Test
    @Order(1)
    void a_createProducts() {
        product1 = productFactory.create(1L, category1, "Portrait Art", "Digital portrait of a person", 150.0);
        assertNotNull(product1);
        assertEquals("Portrait Art", product1.getTitle());
        assertEquals("Digital portrait of a person", product1.getDescription());
        assertEquals(150.0, product1.getPrice());
        assertEquals(1001L, product1.getCategory().getCategoryId());
        System.out.println("Product1 created: " + product1);

        product2 = productFactory.create(2L, category2, "Abstract Art", "Colorful abstract design", 200.0);
        assertNotNull(product2);
        assertEquals("Abstract Art", product2.getTitle());
        assertEquals("Colorful abstract design", product2.getDescription());
        assertEquals(200.0, product2.getPrice());
        assertEquals(1002L, product2.getCategory().getCategoryId());
        System.out.println("Product2 created: " + product2);
    }

    @Test
    @Order(2)
    void b_copyProduct() {
        Product copy = productFactory.copy(product1);
        assertNotNull(copy);
        assertEquals(product1.getTitle(), copy.getTitle());
        assertEquals(product1.getDescription(), copy.getDescription());
        assertEquals(product1.getPrice(), copy.getPrice());
        assertEquals(product1.getCategory().getCategoryId(), copy.getCategory().getCategoryId());
        System.out.println("Copied product: " + copy);
    }
}
