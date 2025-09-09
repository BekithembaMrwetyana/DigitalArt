package za.ac.cput.service;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import za.ac.cput.domain.Category;
import za.ac.cput.domain.Product;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductServiceTest {

    @Autowired
    private IProductService productService;

    @Autowired
    private ICategoryService categoryService;


    private Category category1, category2, category3;


    private Product product1, product2, product3;


    @BeforeAll
    void setUp() {

        category1 = categoryService.create(new Category.Builder().setName("3D").setDescription("3D artworks").build());
        category2 = categoryService.create(new Category.Builder().setName("Abstract").setDescription("Abstract artworks").build());

        product1 = productService.create(new Product.Builder()
                .setCategory(category1)
                .setTitle("3D Sculpture")
                .setDescription("Digital 3D sculpture")
                .setPrice(159.99)
                .setImageUrl("/images/art8.jpeg")
                .build()
        );

        product2 = productService.create(new Product.Builder()
                .setCategory(category2)
                .setTitle("Abstract Art ")
                .setDescription("Colorful abstract design")
                .setPrice(199.99)
                .setImageUrl("/images/art9.jpeg")
                .build()
        );


        category3 = categoryService.create(new Category.Builder().setName("Landscape").setDescription("Landscape artworks").build());


        product3 = productService.create(new Product.Builder()
                .setCategory(category3)
                .setTitle("Ocean Waves")
                .setDescription("Beautiful ocean landscape")
                .setPrice(179.99)
                .setImageUrl("/images/art10.jpeg")
                .build()
        );
    }

    @Test
    @Order(1)
    void testCreateProducts() {
        assertNotNull(product1.getProductID());
        assertNotNull(product2.getProductID());
        assertNotNull(product3.getProductID());


        System.out.println("Created products: " + product1 + ", " + product2 + ", " + product3 + ",");
    }

    @Test
    @Order(2)
    void testGetAllProducts() {
        List<Product> all = productService.getAll();
        assertEquals(9, all.size());
        System.out.println("All products: " + all);
    }

    @Test
    @Order(3)
    void testGetByCategory() {
        List<Product> byCategory = productService.getByCategoryId(category1.getCategoryId());
        assertFalse(byCategory.isEmpty());
        System.out.println("Products by category " + category1.getName() + ": " + byCategory);
    }

    @Test
    @Order(4)
    void testSaveImagesBatch() throws IOException {
        Product[] products = {product1, product2, product3};
        for (Product p : products) {
            // Get filename from product's imageUrl
            String fileName = Paths.get(p.getImageUrl()).getFileName().toString();

            Path path = Paths.get("src/main/resources/static/images/" + fileName);
            assertTrue(Files.exists(path), "Test image must exist: " + path.toAbsolutePath());

            MultipartFile file = new MockMultipartFile(
                    "file",
                    fileName,
                    "image/jpeg",
                    Files.readAllBytes(path)
            );

            Product updated = productService.saveImage(p.getProductID(), file);

            assertNotNull(updated.getImageData(), "Image data should be persisted in DB");
            assertTrue(updated.getImageData().length > 0, "Image bytes should not be empty");
            assertEquals(p.getImageUrl(), updated.getImageUrl(), "Image URL should remain correct");

            System.out.println("Saved image for product: " + updated.getTitle() +
                    ", bytes length: " + updated.getImageData().length);
        }
    }

    @Test
    @Order(5)
    void testUpdateProductBatch() {
        Product updated = new Product.Builder().copy(product2).setPrice(250.0).setTitle("Abstract Art Updated").build();
        Product result = productService.update(updated);
        assertEquals(250.0, result.getPrice());
        System.out.println("Updated product: " + result);
    }

    @Test
    @Order(6)
    void testDeleteProductsBatch() {
        Product[] products = {product1, product2, product3};
        for (Product p : products) {
            productService.delete(p.getProductID());
            assertNull(productService.read(p.getProductID()));
            System.out.println("Deleted product: " + p.getTitle());
        }
    }
}
