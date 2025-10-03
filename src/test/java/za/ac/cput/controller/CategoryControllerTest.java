package za.ac.cput.controller;
/*
CategoryControllerTest class
Author: Abethu Ngxitho
Date: 29 August 2025


 */

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
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


    private static final String BASE_URL = "/api/categories";

    @BeforeAll
    public static void setup() {
        category = CategoryFactory.createCategory(
                "Wall Art",
                "A beautiful piece that adds elegance to any space"
        );
    }

    @Test
    @Order(1)
    void a_create() {
        String url = BASE_URL + "/create";
        Category createdCategory = this.restTemplate.postForObject(url, category, Category.class);
        assertNotNull(createdCategory);
        assertEquals(category.getName(), createdCategory.getName());
        category = createdCategory;
        System.out.println("Created: " + createdCategory);
    }

    @Test
    @Order(2)
    void b_read() {
        String url = BASE_URL + "/read/" + category.getCategoryId();
        ResponseEntity<Category> response = this.restTemplate.getForEntity(url, Category.class);
        assertNotNull(response.getBody());
        assertEquals(category.getCategoryId(), response.getBody().getCategoryId());
        System.out.println("Read: " + response.getBody());
    }

    @Test
    @Order(3)
    void c_update() {
        Category updatedCategory = new Category.Builder().copy(category)
                .setName("Abstract Art")
                .setDescription("Digital art using shapes and colors")
                .build();

        HttpEntity<Category> request = new HttpEntity<>(updatedCategory);
        ResponseEntity<Category> response = restTemplate.exchange(
                BASE_URL + "/update",
                HttpMethod.PUT,
                request,
                Category.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Abstract Art", response.getBody().getName());
        assertEquals("Digital art using shapes and colors", response.getBody().getDescription());

        category = response.getBody();
        System.out.println("Updated: " + category);
    }

    @Test
    @Order(4)
    void d_getAll() {
        String url = BASE_URL + "/getAll";
        ResponseEntity<Category[]> response = this.restTemplate.getForEntity(url, Category[].class);
        assertNotNull(response.getBody());
        assertTrue(response.getBody().length > 0, "Should return at least one category");
        System.out.println("Get All:");
        for (Category c : response.getBody()) {
            System.out.println(c);
        }
    }

    @Test
    @Order(5)
    void e_delete() {
        String url = BASE_URL + "/" + category.getCategoryId();
        ResponseEntity<Void> response = restTemplate.exchange(url, HttpMethod.DELETE, null, Void.class);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        System.out.println("Deleted category with ID: " + category.getCategoryId());
    }
}
