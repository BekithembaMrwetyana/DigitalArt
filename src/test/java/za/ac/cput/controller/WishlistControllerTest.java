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
    private static final String BASE_URL = "http://localhost:8080/ADP_Capstone_Project/wishlist";

    @BeforeAll
    public static void setup() {
        User user = new User.Builder()
                .setUserId(1L)
                .setFirstName("John")
                .setLastName("Doe")
                .build();

        wishlist = WishlistFactory.createWishlist(1L, user, new ArrayList<>());
    }

    @Test
    @Order(1)
    void a_create() {
        String url = BASE_URL + "/create";
        Wishlist createdWishlist = restTemplate.postForObject(url, wishlist, Wishlist.class);
        assertNotNull(createdWishlist);
        assertEquals(wishlist.getWishlistID(), Objects.requireNonNull(createdWishlist).getWishlistID());
        wishlist = createdWishlist; // save generated ID
        System.out.println("Created: " + createdWishlist);
    }

    @Test
    @Order(2)
    void b_read() {
        String url = BASE_URL + "/read/" + wishlist.getWishlistID();
        ResponseEntity<Wishlist> response = restTemplate.getForEntity(url, Wishlist.class);
        assertEquals(wishlist.getWishlistID(), response.getBody().getWishlistID());
        System.out.println("Read: " + response.getBody());
    }

    @Test
    @Order(3)
    void c_update() {
        List<Product> newProducts = new ArrayList<>();
        Product product = new Product.Builder()
                .setProductID(123L)
                .setTitle("Updated Product")
                .setDescription("Updated Description")
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
        wishlist = response.getBody(); // save updated state
        System.out.println("Updated: " + response.getBody());
    }

    @Test
    @Order(4)
    void d_getAll() {
        String url = BASE_URL + "/getAll";
        ResponseEntity<Wishlist[]> response = restTemplate.getForEntity(url, Wishlist[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        System.out.println("Get All:");
        for (Wishlist w : response.getBody()) {
            System.out.println(w);
        }
    }

    @Test
    @Order(5)
    void e_delete() {
        String url = BASE_URL + "/delete/" + wishlist.getWishlistID();
        this.restTemplate.delete(url);

        ResponseEntity<Wishlist> response = restTemplate.getForEntity(
                BASE_URL + "/read/" + wishlist.getWishlistID(),
                Wishlist.class
        );
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        System.out.println("Deleted: true");
    }
}
