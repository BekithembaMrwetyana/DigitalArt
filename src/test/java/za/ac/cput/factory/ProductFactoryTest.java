package za.ac.cput.factory;

/*
ProductFactoryTest.java
Product Factory Test class
Author: Thimna Gogwana 222213973
Date: 25 May 2025
*/

import org.junit.jupiter.api.*;
import za.ac.cput.domain.Category;
import za.ac.cput.domain.Product;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductFactoryTest {

    private ProductFactory productFactory;

    private Product product1;
    private Product product2;
    private Category category1;
    private Category category2;

    @BeforeAll
    void setUp() {

        productFactory = new ProductFactory();


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
        product1 = productFactory.create(1L, category1, "Portrait Art",
                "Digital portrait of a person", 150.0, "art6.jpeg");

        product2 = productFactory.create(2L, category2, "Abstract Art",
                "Colorful abstract design", 200.0, "art5.jpeg");

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

        assertNotNull(copy1);
        assertNotSame(product1, copy1);
        assertEquals(product1.getTitle(), copy1.getTitle());
        assertEquals(product1.getCategory(), copy1.getCategory());

        assertNotNull(copy2);
        assertNotSame(product2, copy2);
        assertEquals(product2.getTitle(), copy2.getTitle());
        assertEquals(product2.getCategory(), copy2.getCategory());
    }

    @Test
    @Order(3)
    void c_updateProductImage() {
        byte[] fakeImageData = {1, 2, 3, 4, 5};

        Product updatedProduct = productFactory.updateImage(product1, "updated-art.jpeg", fakeImageData);

        assertNotNull(updatedProduct);
        assertEquals(product1.getProductID(), updatedProduct.getProductID());
        assertEquals("/images/updated-art.jpeg", updatedProduct.getImageUrl());
        assertArrayEquals(fakeImageData, updatedProduct.getImageData());


        assertNotSame(product1, updatedProduct);
    }
}

