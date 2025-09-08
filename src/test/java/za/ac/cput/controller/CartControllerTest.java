package za.ac.cput.controller;

import za.ac.cput.domain.Cart;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import za.ac.cput.domain.CartItem;
import za.ac.cput.domain.User;
import za.ac.cput.factory.CartFactory;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.MethodName.class)
class CartControllerTest {

    private static Cart cart;

    @Autowired
    private TestRestTemplate restTemplate;

    private static final String BASE_URL = "/cart";

    @BeforeAll
    public static void setup() {
        User user = new User.Builder()
                .setUserId(1L)
                .setFirstName("John")
                .setLastName("Doe")
                .setPassword("password123")
                .build();

        List<CartItem> cartItems = new ArrayList<>();

        cart = CartFactory.createCart(user, cartItems);
    }


    @Test
    @Order(1)
    void create() {
        String url = BASE_URL + "/create";
        ResponseEntity<Cart> postResponse = this.restTemplate.postForEntity(url, cart, Cart.class);

        assertNotNull(postResponse);
        Cart cartSaved = postResponse.getBody();

        assertNotNull(cartSaved);
        assertNotNull(cartSaved.getCartID());
        cart = cartSaved;

        System.out.println("Created: " + cartSaved);
    }

    @Test
    @Order(2)
    void read() {
        String url = BASE_URL + "/read/" + cart.getCartID();
        ResponseEntity<Cart> response = this.restTemplate.getForEntity(url, Cart.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(cart.getCartID(), response.getBody().getCartID());

        System.out.println("Read: " + response.getBody());
    }

    @Test
    @Order(3)
    void update() {
        Cart updatedCart = new Cart.Builder().copy(cart).build(); // keep same ID
        String url = BASE_URL + "/update";
        this.restTemplate.put(url, updatedCart);

        ResponseEntity<Cart> response = this.restTemplate.getForEntity(BASE_URL + "/read/" + updatedCart.getCartID(), Cart.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        System.out.println("Updated: " + response.getBody());
    }

    @Test
    @Order(4)
    void delete() {
        String url = BASE_URL + "/delete/" + cart.getCartID();
        this.restTemplate.delete(url);

        ResponseEntity<String> response = this.restTemplate.getForEntity(BASE_URL + "/read/" + cart.getCartID(), String.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode()); // adjust if controller sends 400

        System.out.println("Deleted: " + cart.getCartID());
    }

    @Test
    @Order(5)
    void getAll() {
        String url = BASE_URL + "/getAll";
        ResponseEntity<Cart[]> response = this.restTemplate.getForEntity(url, Cart[].class);

        assertNotNull(response.getBody());
        //assertTrue(response.getBody().length >= 0);

        System.out.println("Get All: " + response.getBody().length + " carts");
    }
}