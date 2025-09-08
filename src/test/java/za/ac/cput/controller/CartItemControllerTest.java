package za.ac.cput.controller;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import za.ac.cput.domain.*;
import za.ac.cput.service.*;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CartItemControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserService userService;

    @Autowired
    private CartService cartService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CartItemService cartItemService;

    private final String baseUrl = "/cart_item";

    private User savedUser;
    private Cart savedCart;
    private Category savedCategory;
    private Product savedProduct;
    private CartItem savedCartItem;

    @BeforeAll
    void init() {

        savedUser = userService.create(
                new User.Builder()
                        .setFirstName("Thando")
                        .setLastName("Mseleku")
                        .setPassword("password123")
                        .build()
        );


        savedCategory = categoryService.create(
                new Category.Builder()
                        .setName("Art")
                        .setDescription("Digital Art Category")
                        .build()
        );


        savedProduct = productService.create(
                new Product.Builder()
                        .setTitle("Digital Art")
                        .setDescription("Beautiful painting")
                        .setPrice(49.99)
                        .setCategory(savedCategory)
                        .build()
        );


        savedCart = cartService.create(
                new Cart.Builder()
                        .setUser(savedUser)
                        .build()
        );
    }

    @Test
    @Order(1)
    void a_createCartItem() {
        CartItem cartItem = new CartItem.Builder()
                .setUser(savedUser)
                .setCart(savedCart)
                .setProduct(savedProduct)
                .setQuantity(5)
                .setPrice(49.99)
                .build();

        ResponseEntity<CartItem> response = restTemplate.
                postForEntity(
                baseUrl + "/create",
                cartItem,
                CartItem.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getCartItemID());

        savedCartItem = response.getBody();
        System.out.println("Created CartItem: " + savedCartItem);
    }

    @Test
    @Order(2)
    void b_readCartItem() {
        CartItem cartItem = restTemplate.getForObject(
                baseUrl + "/read/" + savedCartItem.getCartItemID(),
                CartItem.class
        );
        assertNotNull(cartItem);
        System.out.printf("CartItem: %s\n", cartItem);
    }

    @Test
    @Order(3)
    void c_updateCartItem() {

        CartItem updatedCartItem = new CartItem.Builder()
                .copy(savedCartItem)
                .setQuantity(10)
                .build();

        ResponseEntity<CartItem> response = restTemplate.postForEntity(
                baseUrl + "/update",
                updatedCartItem,
                CartItem.class
        );

        assertNotNull(response.getBody());
        savedCartItem = response.getBody();
        System.out.printf("Updated cart item :" + updatedCartItem);
    }

    @Test
    @Order(4)
    void d_getAllCartItems() {
        cartItemService.getAll();
        System.out.printf("CartItem: ", savedCartItem);
    }

    @Test
    @Order(5)
    void e_deleteCartItem() {
        restTemplate.delete(baseUrl + "/delete/" + savedCartItem.getCartItemID());

    }
}

