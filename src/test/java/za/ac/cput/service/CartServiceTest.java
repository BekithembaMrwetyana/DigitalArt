package za.ac.cput.service;

import org.springframework.beans.factory.annotation.Autowired;
import za.ac.cput.domain.Cart;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import za.ac.cput.domain.CartItem;
import za.ac.cput.domain.User;
import za.ac.cput.factory.CartFactory;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
class CartServiceTest {

    @Autowired
    private ICartService service;

    private static Cart cart;

    @BeforeEach
    void setup() {
        User user = new User.Builder()
                .setUserId(1L)
                .build();

        List<CartItem> cartItems = new ArrayList<>();

        cart = CartFactory.createCart(user, cartItems);
    }
    @Test
    @Order(1)
    void create() {
        Cart newCart = service.create(cart);
        assertNotNull(newCart);
        System.out.println(newCart);
    }

    @Test
    @Order(2)
    void read() {
        Cart read = service.read(cart.getCartID());
        assertNotNull(read);
        System.out.print(read);
    }

    @Test
    @Order(3)
    void update() {
        Cart newCart = new Cart.Builder().copy(cart).setCartID(123L).build();
        Cart updated = service.update(newCart);
        assertNotNull(updated);
        System.out.println(updated);
    }

    @Test
    @Order(4)
    void delete() {

    }

    @Test
    @Order(5)
    void getAll() {
        System.out.println(service.getAll());
    }
}