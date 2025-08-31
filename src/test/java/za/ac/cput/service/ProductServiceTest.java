package za.ac.cput.service;
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
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductServiceTest {

    @Autowired
    private IProductService productService;

    private Product product1;
    private Product product2;

    private Category category1;
    private Category category2;

    @BeforeAll
    void setUp() {
        // Create categories
        category1 = new Category.Builder()
                .setName("3D")
                .setDescription("3D artworks")
                .build();

        category2 = new Category.Builder()
                .setName("Abstract")
                .setDescription("Abstract artworks")
                .build();

        // Create products
        product1 = productService.create(
                new Product.Builder()
                        .setCategory(category1)
                        .setTitle("Abstract Art")
                        .setDescription("Digital portrait of a person")
                        .setPrice(150.0)
                        .setImageUrl("/images/art5.jpeg")
                        .build()
        );

        product2 = productService.create(
                new Product.Builder()
                        .setCategory(category2)
                        .setTitle("Abstract Art")
                        .setDescription("Colorful abstract design")
                        .setPrice(200.0)
                        .setImageUrl("/images/art3.jpeg")
                        .build()
        );
    }

    @Test
    @Order(1)
    void testCreateProducts() {
        assertNotNull(product1.getProductID());
        assertNotNull(product2.getProductID());
        System.out.println("Created products: " + product1 + ", " + product2);
    }

    @Test
    @Order(2)
    void testReadProduct() {
        Product read = productService.read(product1.getProductID());
        assertNotNull(read);
        assertEquals(150.0, read.getPrice());
        assertEquals(category1.getCategoryId(), read.getCategory().getCategoryId());
        System.out.println("Read product: " + read);
    }

    @Test
    @Order(3)
    void testUpdateProduct() {
        Product productFromDb = productService.read(product2.getProductID());

        Product updated = new Product.Builder()
                .copy(productFromDb)
                .setPrice(250.0)
                .setTitle("Abstract Art Updated")
                .build();

        Product result = productService.update(updated);

        assertNotNull(result);
        assertEquals(250.0, result.getPrice());
        assertEquals("Abstract Art Updated", result.getTitle());
        System.out.println("Updated product: " + result);
    }

    @Test
    @Order(4)
    void testGetAll() {
        List<Product> all = productService.getAll();
        assertFalse(all.isEmpty());
        System.out.println("All products: " + all);
    }

    @Test
    @Order(5)
    void testGetByCategory() {
        List<Product> byCategory = productService.getByCategoryId(category1.getCategoryId());
        assertFalse(byCategory.isEmpty());
        assertEquals(category1.getCategoryId(), byCategory.get(0).getCategory().getCategoryId());
        System.out.println("Products by category " + category1.getCategoryId() + ": " + byCategory);
    }

    @Test
    @Order(6)
    void testSearchByTitle() {
        List<Product> found = productService.searchByTitle("Portrait");
        assertFalse(found.isEmpty());
        System.out.println("Products found with 'Portrait': " + found);
    }

    @Test
    @Order(7)
    void testFilterByPrice() {
        List<Product> filtered = productService.filterByPrice(100.0, 200.0);
        assertFalse(filtered.isEmpty());
        System.out.println("Products between 100 and 200: " + filtered);
    }

    @Test
    @Order(8)
    void testFilterByMaxPrice() {
        List<Product> filtered = productService.filterByMaxPrice(200.0);
        assertFalse(filtered.isEmpty());
        System.out.println("Products with price <= 200: " + filtered);
    }

    @Test
    @Order(9)
    void testSaveImage() throws IOException {
        // Ensure product exists
        Product product = productService.read(product1.getProductID());
        assertNotNull(product, "Product should exist before uploading image");

        // Prepare the test imagePath from resources
        Path path = Paths.get("src/main/resources/static/images/art1.jpeg");
        assertTrue(Files.exists(path), "Test image must exist at " + path.toAbsolutePath());

        // Create MultipartFile for simulation
        MultipartFile file = new MockMultipartFile(
                "file",
                "art1.jpeg",
                "image/jpeg",
                Files.readAllBytes(path)
        );

        // Save the image using service
        Product updatedProduct = productService.saveImage(product.getProductID(), file);

        // Assertions
        assertNotNull(updatedProduct, "Updated product should not be null");
        assertNotNull(updatedProduct.getImageUrl(), "Image URL should be set after upload");
        assertTrue(updatedProduct.getImageUrl().contains("art1.jpeg"), "Image URL should contain the uploaded filename");

        // Check encoded Base64 image
        assertNotNull(updatedProduct.getImage(), "Encoded Base64 image should not be null");
        assertTrue(updatedProduct.getImage().startsWith("/9j"), "Encoded image should look like Base64 JPEG"); // JPEG files usually start with /9j

        // Print result for visibility
        System.out.println("Saved Image URL: " + updatedProduct.getImageUrl());
        System.out.println("Encoded Image (first 50 chars): " + updatedProduct.getImage().substring(0, 50));
    }

    @Test
    @Order(10)
    void testDeleteProduct() {
        productService.delete(product1.getProductID());
        Product deleted = productService.read(product1.getProductID());
        assertNull(deleted, "Deleted product should not exist");
        System.out.println("Deleted product with ID: " + product1.getProductID());
    }
}
