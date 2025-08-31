package za.ac.cput.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import za.ac.cput.domain.Category;
import za.ac.cput.domain.Product;
import za.ac.cput.service.CategoryService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ProductControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private static Product product;
    private static Category category;

    private final String BASE_URL = "/api/products";

    @BeforeAll
    void setup(@Autowired CategoryService categoryService) {
        // Create category
        category = new Category.Builder()
                .setName("Digital Art")
                .setDescription("Digital artwork and illustrations")
                .build();
        category = categoryService.create(category);

        // Create product
        product = new Product.Builder()
                .setTitle("Neon Dreams")
                .setDescription("A stunning digital artwork exploring dreams and reality")
                .setPrice(299.99)
                .setImageUrl("/images/art1.jpeg")
                .setCategory(category)
                .build();
    }

    @Test
    @Order(1)
    void a_create() {
        ResponseEntity<Product> response = restTemplate.postForEntity(BASE_URL, product, Product.class);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(product.getTitle(), response.getBody().getTitle());
        product = response.getBody(); // update ID
        System.out.println("Created: " + product);
    }

    @Test
    @Order(2)
    void b_read() {
        ResponseEntity<Product> response = restTemplate.getForEntity(BASE_URL + "/" + product.getProductID(), Product.class);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(product.getProductID(), response.getBody().getProductID());
        System.out.println("Read: " + response.getBody());
    }

    @Test
    @Order(3)
    void c_update() {
        Product updatedProduct = new Product.Builder()
                .copy(product)
                .setTitle("Updated Neon Dreams")
                .setPrice(349.99)
                .build();

        HttpEntity<Product> request = new HttpEntity<>(updatedProduct);
        ResponseEntity<Product> response = restTemplate.exchange(BASE_URL + "/" + product.getProductID(), HttpMethod.PUT, request, Product.class);

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Updated Neon Dreams", response.getBody().getTitle());
        product = response.getBody();
        System.out.println("Updated: " + response.getBody());
    }

    @Test
    @Order(4)
    void d_getAll() {
        ResponseEntity<Product[]> response = restTemplate.getForEntity(BASE_URL, Product[].class);
        assertNotNull(response.getBody());
        assertTrue(response.getBody().length > 0);
        System.out.println("Get All:");
        for (Product p : response.getBody()) System.out.println(p);
    }

    @Test
    @Order(5)
    void e_getProductsByCategory() {
        ResponseEntity<List<Product>> response = restTemplate.exchange(
                BASE_URL + "/category/" + category.getCategoryId(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Product>>() {}
        );
        assertNotNull(response.getBody());
        assertTrue(response.getBody().size() > 0, "Products by category should not be empty");
        System.out.println("Products by Category: " + response.getBody());
    }

    @Test
    @Order(6)
    void f_searchProducts() {
        ResponseEntity<List<Product>> response = restTemplate.exchange(
                BASE_URL + "/search?keyword=Neon",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Product>>() {}
        );
        assertNotNull(response.getBody());
        assertTrue(response.getBody().size() > 0, "Search results should not be empty");
        System.out.println("Search Results: " + response.getBody());
    }

    @Test
    @Order(7)
    void g_uploadImage() throws IOException {
        // Use the product already created in @BeforeAll
        assertNotNull(product, "Product must exist before uploading image");

        // Prepare the file to upload
        Path path = Paths.get("src/main/resources/static/images/art1.jpeg");
        assertTrue(Files.exists(path), "Test image must exist at " + path.toAbsolutePath());

        // Wrap the file in a MultiValueMap for multipart request
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", new FileSystemResource(path.toFile()));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        // Call the upload-image endpoint
        ResponseEntity<Product> response = restTemplate.postForEntity(
                BASE_URL + "/" + product.getProductID() + "/upload-image",
                requestEntity,
                Product.class
        );

        // Assert that the response contains the updated product with image URL
        assertNotNull(response.getBody(), "Response body should not be null");
        assertNotNull(response.getBody().getImageUrl(), "Image URL should not be null");
        assertTrue(response.getBody().getImageUrl().contains("/images/"), "Image URL should contain '/images/'");

        System.out.println("Uploaded Image: " + response.getBody().getImageUrl());

        // Update the product reference for future tests
        product = response.getBody();
    }

    @Test
    @Order(8)
    void h_delete() {
        restTemplate.delete(BASE_URL + "/" + product.getProductID());
        ResponseEntity<Product> response = restTemplate.getForEntity(BASE_URL + "/" + product.getProductID(), Product.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        System.out.println("Deleted product with ID: " + product.getProductID());
    }


}