package za.ac.cput.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.ac.cput.domain.Product;
import za.ac.cput.factory.ProductFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    private static Product product;

//    @BeforeEach
//    void setup() {
//        product = ProductFactory.createFullProduct(
//                "Laptop",
//                "Gaming Laptop",
//                1500.00
//        );
//    }
//
//    @Test
//    void create() {
//        Product created = productService.create(product);
//        assertNotNull(created);
//        assertEquals(product.getProductId(), created.getProductId());
//        System.out.println("Created: " + created);
//    }
//
//    @Test
//    void read() {
//        Product saved = productService.create(product); // ensure it exists
//        Product read = productService.read(saved.getProductId());
//        assertNotNull(read);
//        assertEquals(saved.getProductId(), read.getProductId());
//        System.out.println("Read: " + read);
//    }
//
//    @Test
//    void update() {
//        Product saved = productService.create(product);
//
//        Product updatedProduct = new Product.Builder()
//                .copy(saved)
//                .setName("High-End Laptop")
//                .build();
//
//        Product updated = productService.update(updatedProduct);
//        assertNotNull(updated);
//        assertEquals("High-End Laptop", updated.getName());
//        System.out.println("Updated: " + updated);
//    }
//
//    @Test
//    void getAll() {
//        productService.create(product);
//        List<Product> products = productService.getAll();
//        assertFalse(products.isEmpty());
//        System.out.println("All Products: " + products);
//    }
//
//    @Test
//    void delete() {
//        Product saved = productService.create(product);
//        productService.delete(saved.getProductId());
//
//        Product deleted = productService.read(saved.getProductId());
//        assertNull(deleted);
//        System.out.println("Deleted Product ID: " + saved.getProductId());
//    }
}
