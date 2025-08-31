
package za.ac.cput.service;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.ac.cput.domain.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CartItemServiceTest {

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private UserService userService;

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    private User savedUser;
    private Cart savedCart;
    private Category savedCategory;
    private Product savedProduct;
    private CartItem savedCartItem;


    @BeforeAll
    void setup() {



        savedUser = userService.create(new User.Builder()
                .setFirstName("Thando")
                .setLastName("Mseleku")
                .setPassword("password123")
                .setLastLogin(LocalDateTime.now())
                .setCreateDate(LocalDate.of(2025,02,25))
                .build());


        savedCategory = categoryService.create(new Category.Builder()
                .setName("Animalz")
                .setDescription("Picture of monkeys laughing at eac other")
                .build());


        savedProduct = productService.create(new Product.Builder()
                .setTitle("Digital Art")
                .setDescription("funny animal picture")
                .setPrice(10.000)
                .setCategory(savedCategory)
                .build());


        savedCart = cartService.create(new Cart.Builder()
                .setUser(savedUser)
                .build());
    }

    @Test
    @Order(1)
    void testCreateCartItem() {
        CartItem cartItem = new CartItem.Builder()
                .setUser(savedUser)
                .setCart(savedCart)
                .setProduct(savedProduct)
                .setQuantity(5)
                .setPrice(10.000)
                .build();

        savedCartItem = cartItemService.create(cartItem);

        assertNotNull(savedCartItem);
        assertNotNull(savedCartItem.getCartItemID());
        System.out.println("Created CartItem: " + savedCartItem);
    }

    @Test
    @Order(2)
    @Transactional
    void testReadCartItem() {
        CartItem cartItem = cartItemService.read(savedCartItem.getCartItemID());
        assertNotNull(cartItem);
        assertEquals(5, cartItem.getQuantity());
        System.out.println( cartItem);
    }

    @Test
    @Order(3)
    void testUpdateCartItem() {
        CartItem updatedCartItem = new CartItem.Builder()
                .copy(savedCartItem)
                .setQuantity(10)
                .build();

        savedCartItem = cartItemService.update(updatedCartItem);
        assertEquals(10, savedCartItem.getQuantity());
        System.out.println("Updated CartItem: " + savedCartItem);

        assertNotNull(savedCartItem, "savedCartItem must not be null before update");

    }

    @Test
    @Order(4)
    void testGetAllCartItems() {
        List<CartItem> allItems = cartItemService.getAll();
        assertFalse(allItems.isEmpty());
        System.out.println("All CartItems: " + allItems);
    }

    @Test
    @Order(5)
    void testDeleteCartItem() {
        cartItemService.delete(savedCartItem.getCartItemID());
        CartItem deleted = cartItemService.read(savedCartItem.getCartItemID());
        assertNull(deleted);
        System.out.println("Deleted CartItem with ID: " + savedCartItem.getCartItemID());
    }
}