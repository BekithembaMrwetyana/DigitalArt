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
                .setCategoryId(1001L) // fake ID for testing, not persisted
                .setName("Portraits")
                .setDescription("Portrait artworks")
                .build();

        category2 = new Category.Builder()
                .setCategoryId(1002L) // fake ID for testing, not persisted
                .setName("Abstract")
                .setDescription("Abstract artworks")
                .build();
    }

    @Test
    @Order(1)
    void a_createProducts() {
        product1 = productFactory.create(1L, category1, "Portrait Art", "Digital portrait of a person", 150.0, "art6.jpeg");
        product2 = productFactory.create(2L, category2, "Abstract Art", "Colorful abstract design", 200.0, "art5.jpeg");

        assertNotNull(product1);
        assertEquals("Portrait Art", product1.getTitle());
        assertEquals("/images/art6.jpeg", product1.getImageUrl());

        assertNotNull(product2);
        assertEquals("Abstract Art", product2.getTitle());
        assertEquals("/images/art5.jpeg", product2.getImageUrl());
    }

    @Test
    @Order(2)
    void b_copyProduct() {
        Product copy1 = productFactory.copy(product1);
        Product copy2 = productFactory.copy(product2);

        assertEquals(product1.getTitle(), copy1.getTitle());
        assertEquals(product2.getTitle(), copy2.getTitle());
    }
}
