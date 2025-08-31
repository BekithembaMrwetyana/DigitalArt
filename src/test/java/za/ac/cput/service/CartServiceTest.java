package za.ac.cput.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.ac.cput.domain.Cart;
import za.ac.cput.domain.CartItem;
import za.ac.cput.domain.User;
import za.ac.cput.factory.CartFactory;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CartServiceTest {

    @Autowired
    private ICartService service;

    private static Cart cart;
    private static User user;

    @BeforeAll
    static void setUpAll() {
        user = new User.Builder()
                .setFirstName("Alex")
                .setLastName("McConnor")
                .build();

        List<CartItem> cartItems = new ArrayList<>();
        cart = CartFactory.createCart(user, cartItems);
        assertNotNull(cart);
        System.out.println("Initial Cart created: " + cart);
    }

    @Test
    @Order(1)
    void a_create() {
        cart = service.create(cart); // persist so cartID is generated
        assertNotNull(cart);
        assertNotNull(cart.getCartID(), "Cart ID should not be null after creation");
        System.out.println("Created Cart: " + cart);
    }

    @Test
    @Order(2)
    void b_read() {
        assertNotNull(cart.getCartID(), "Cart ID should not be null before read");
        Cart read = service.read(cart.getCartID());
        assertNotNull(read, "Read Cart should not be null");
        System.out.println("Read Cart: " + read);
    }

    @Test
    @Order(3)
    void c_update() {
        Cart updatedCart = new Cart.Builder().copy(cart)
                .setCartID(cart.getCartID()) // use the persisted cartID
                .build();
        Cart result = service.update(updatedCart);
        assertNotNull(result);
        System.out.println("Updated Cart: " + result);
    }

    @Test
    @Order(4)
    void d_delete() {
        assertNotNull(cart.getCartID(), "Cart ID should not be null before deletion");
        service.delete(cart.getCartID());
        Cart deleted = service.read(cart.getCartID());
        assertNull(deleted, "Deleted Cart should be null");
        System.out.println("Cart deleted successfully");
    }

    @Test
    @Order(5)
    void e_getAll() {
        System.out.println("All Carts: " + service.getAll());
    }
}
