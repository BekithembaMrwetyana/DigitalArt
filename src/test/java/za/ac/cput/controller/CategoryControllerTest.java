package za.ac.cput.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import za.ac.cput.domain.Category;
import za.ac.cput.factory.CategoryFactory;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.MethodName.class)

class CategoryControllerTest {

    private static Category category;

    @Autowired
    private TestRestTemplate restTemplate;

    private static final String BASE_URL = "/category";

    @BeforeAll
    public static void setup() {
        category = CategoryFactory.createCategory("Wall Art" ,  "A beautiful piece that adds elegance to any space");
    }

    @Test
    @Order(1)
    void a_create() {
        String url = BASE_URL + "/create";
        Category createdCategory = this.restTemplate.postForObject(url, category, Category.class);
        assertNotNull(createdCategory);
        assertEquals(category.getName(), createdCategory.getName());
        category = createdCategory;
        System.out.println("created: " + createdCategory);
    }

    @Test
    @Order(2)
    void b_read() {
        String url = BASE_URL + "/read/" + category.getCategoryId();
        ResponseEntity<Category> response = this.restTemplate.getForEntity(url, Category.class);
        assertNotNull(response.getBody());
        assertEquals(category.getCategoryId(), response.getBody().getCategoryId());
        System.out.println("read: " + response.getBody());
    }

    @Test
    @Order(3)
    void c_update() {
        Category updatedCategory = new Category.Builder().copy(category)
                .setName("Abstract Art")
                .setDescription("Digital art using shapes and colors")
                .build();

        String url = BASE_URL + "/update";
        ResponseEntity<Category> response = this.restTemplate.postForEntity(url, updatedCategory, Category.class);
        assertNotNull(response.getBody());
        assertEquals(updatedCategory.getName(), response.getBody().getName());
        category = response.getBody();
        System.out.println("updated:" + response.getBody());

    }

    @Test
    @Order(5)
    void e_delete() {
        String url = BASE_URL + "/delete/" + category.getCategoryId();
        this.restTemplate.delete(url);

        ResponseEntity<Category> response = this.restTemplate.getForEntity( BASE_URL + "/read/" + category.getCategoryId(), Category.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        System.out.println("Deleted:" + "true");
    }

    @Test
    @Order(4)
    void d_getAll() {
        String url = BASE_URL + "/getAll";
        ResponseEntity<Category[]> response = this.restTemplate. getForEntity(url, Category[].class);
        assertNotNull(response.getBody());
        System.out.println("Get All; ");
        for (Category category : response.getBody()) {
            System.out.println(category);
        }
    }
}