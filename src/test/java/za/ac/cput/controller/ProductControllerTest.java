package za.ac.cput.controller;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import za.ac.cput.domain.Category;
import za.ac.cput.domain.Product;
import za.ac.cput.service.CategoryService;
import za.ac.cput.service.ICategoryService;
import za.ac.cput.service.IProductService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ProductControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private IProductService productService;

    @Autowired
    private ICategoryService categoryService;

    private static Product testProduct;
    private static Category digitalArtCategory;

    private final String BASE_URL = "/api/products";

    @BeforeAll
    void setup() {
        // Fetch Digital Art category
        digitalArtCategory = categoryService.getAll().stream()
                .filter(c -> c.getName().equals("Digital Art"))
                .findFirst()
                .orElseThrow();


        testProduct = productService.getByCategoryId(digitalArtCategory.getCategoryId()).get(0);
    }

    @Test
    @Order(1)
    void a_getAllProducts() {
        ResponseEntity<Product[]> response = restTemplate.getForEntity(BASE_URL, Product[].class);
        assertNotNull(response.getBody());
        assertTrue(response.getBody().length >= 10, "Should have all seeded products");
        System.out.println("All products:");
        Arrays.stream(response.getBody()).forEach(System.out::println);
    }

    @Test
    @Order(2)
    void b_getProductsByCategory() {
        ResponseEntity<List<Product>> response = restTemplate.exchange(
                BASE_URL + "/category/" + digitalArtCategory.getCategoryId(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Product>>() {}
        );

        assertNotNull(response.getBody());
        assertFalse(response.getBody().isEmpty(), "Products by category should not be empty");
        System.out.println("Products by category: " + response.getBody());
    }

    @Test
    @Order(3)
    void c_searchProducts() {
        String keyword = testProduct.getTitle().split(" ")[0]; // pick first word from title
        ResponseEntity<List<Product>> response = restTemplate.exchange(
                BASE_URL + "/search?keyword=" + keyword,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Product>>() {}
        );

        assertNotNull(response.getBody());
        assertFalse(response.getBody().isEmpty(), "Search results should not be empty");
        System.out.println("Search results for '" + keyword + "': " + response.getBody());
    }

    @Test
    @Order(4)
    void d_updateProduct() {
        Product updated = new Product.Builder()
                .copy(testProduct)
                .setTitle(testProduct.getTitle() + " Updated")
                .setPrice(testProduct.getPrice() + 50.0)
                .build();

        HttpEntity<Product> request = new HttpEntity<>(updated);
        ResponseEntity<Product> response = restTemplate.exchange(
                BASE_URL + "/" + testProduct.getProductID(),
                HttpMethod.PUT,
                request,
                Product.class
        );

        assertNotNull(response.getBody());
        assertEquals(updated.getTitle(), response.getBody().getTitle());
        System.out.println("Updated product: " + response.getBody());

        testProduct = response.getBody();
    }

    @Test
    @Order(5)
    void e_uploadImage() throws IOException {
        Path path = Paths.get("src/main/resources/static/images/" +
                Paths.get(testProduct.getImageUrl()).getFileName().toString());
        assertTrue(Files.exists(path), "Test image must exist at " + path.toAbsolutePath());

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", new FileSystemResource(path.toFile()));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<Product> response = restTemplate.postForEntity(
                BASE_URL + "/" + testProduct.getProductID() + "/upload-image",
                requestEntity,
                Product.class
        );

        Product updated = response.getBody();
        assertNotNull(updated);
        assertNotNull(updated.getImageData());
        assertTrue(updated.getImageData().length > 0);
        System.out.println("Uploaded image for product: " + updated.getTitle());

        testProduct = updated;
    }

    @Test
    @Order(6)
    void f_getImage() {
        ResponseEntity<byte[]> response = restTemplate.getForEntity(
                BASE_URL + "/" + testProduct.getProductID() + "/image",
                byte[].class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().length > 0);
        System.out.println("Fetched image bytes, size: " + response.getBody().length);
    }

    @Test
    @Order(7)
    void g_deleteProduct() {
        restTemplate.delete(BASE_URL + "/" + testProduct.getProductID());

        ResponseEntity<Product> response = restTemplate.getForEntity(
                BASE_URL + "/" + testProduct.getProductID(),
                Product.class
        );

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        System.out.println("Deleted product with ID: " + testProduct.getProductID());
    }
}
