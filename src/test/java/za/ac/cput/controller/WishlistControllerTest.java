package za.ac.cput.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import za.ac.cput.domain.Product;
import za.ac.cput.domain.User;
import za.ac.cput.domain.Wishlist;
import za.ac.cput.factory.WishlistFactory;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class WishlistControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private static Wishlist wishlist;
    private final String BASE_URL = "http://localhost:8080/wishlist";

    @Test
    @Order(1)
    void create() {
        User user = new User.Builder()
                .setUserId(1L)
                .setFirstName("John")
                .setLastName("Doe")
                .build();
        List<Product> products = new ArrayList<>();

        wishlist = WishlistFactory.createWishlist(1L,  user, new ArrayList<>());

        ResponseEntity<Wishlist> response = restTemplate.postForEntity(
                BASE_URL + "/create", wishlist, Wishlist.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(wishlist.getWishlistID(), Objects.requireNonNull(response.getBody()).getWishlistID());
    }

    @Test
    @Order(2)
    void read() {
        ResponseEntity<Wishlist> response = restTemplate.getForEntity(
                BASE_URL + "/read/" + wishlist.getWishlistID(),
                Wishlist.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(wishlist.getWishlistID(), response.getBody().getWishlistID());
    }

    @Test
    @Order(3)
    void update() {
        List<Product> newProducts = new ArrayList<>();
        Product product = new Product.Builder()
                .setProductID(123L)
                .setTitle("Updated Product")
                .setDescription("Updated")
                .setPrice(100.0)
                .build();
        newProducts.add(product);

        Wishlist updatedWishlist = new Wishlist.Builder()
                .copy(wishlist)
                .setProducts(newProducts)
                .build();

        HttpEntity<Wishlist> entity = new HttpEntity<>(updatedWishlist);
        ResponseEntity<Wishlist> response = restTemplate.exchange(
                BASE_URL + "/update",
                HttpMethod.PUT,
                entity,
                Wishlist.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, Objects.requireNonNull(response.getBody()).getProducts().size());
    }

    @Test
    @Order(4)
    void delete() {
        restTemplate.delete(BASE_URL + "/delete/" + wishlist.getWishlistID());
        ResponseEntity<Wishlist> response = restTemplate.getForEntity(
                BASE_URL + "/read/" + wishlist.getWishlistID(),
                Wishlist.class
        );

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    @Order(5)
    void getAll() {
        ResponseEntity<Wishlist[]> response = restTemplate.getForEntity(
                BASE_URL + "/getAll",
                Wishlist[].class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }
}

