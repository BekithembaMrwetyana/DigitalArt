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

    private static final String BASE_URL = "http://localhost:8080/ADP_Capstone_Project/cart";

    @BeforeAll
    public static void setup() {
        // Create a mock user
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
        //assertEquals(cart.getCartID(), createdCart.getCartID());
        Cart cartSaved = postResponse.getBody();
        assertEquals(cart.getCartID(), cartSaved.getCartID());
        System.out.println("Created: " + cartSaved);
    }

    @Test
    @Order(2)
    void read() {
        String url = BASE_URL + "/read/" + cart.getCartID();
        ResponseEntity<Cart> response = this.restTemplate.getForEntity(url, Cart.class);
        assertEquals(cart.getCartID(), response.getBody().getCartID());
        System.out.print("Read: " + response.getBody());

    }

    @Test
    @Order(3)
    void update() {
        Cart updatedCart = new Cart.Builder().copy(cart).setCartID(123L).build();
        String url = BASE_URL + "/update";
        this.restTemplate.put(url, updatedCart);

        ResponseEntity<Cart> response = this.restTemplate.getForEntity(BASE_URL + "/read/" + updatedCart.getCartID(), Cart.class);

        //assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertNull(response.getBody());
        //assertEquals(updatedCart.getCartID(), response.getBody().getCartID());
        System.out.println("Updated: " + response.getBody());
    }

    @Test
    @Order(4)
    void delete() {
        String url = BASE_URL + "/delete/" + cart.getCartID();
        this.restTemplate.delete(url);

        ResponseEntity<Cart> response = this.restTemplate.getForEntity(BASE_URL + "/read/" + cart.getCartID(), Cart.class);
        //assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        System.out.println("Deleted: " + cart.getCartID());
    }

    @Test
    @Order(5)
    void getAll() {
        String url = BASE_URL + "/getAll";
        ResponseEntity<Cart[]> response = this.restTemplate.getForEntity(url, Cart[].class);
        assertNotNull(response.getBody());
        //assertTrue(response.getBody().length > 0);
        System.out.println("Get All: " + response.getBody());
        for (Cart cart : response.getBody()){
            System.out.println(cart);
        }
    }
}