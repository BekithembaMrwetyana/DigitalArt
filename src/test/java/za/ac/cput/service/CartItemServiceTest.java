package za.ac.cput.service;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.ac.cput.domain.Cart;
import za.ac.cput.domain.CartItem;
import za.ac.cput.domain.Product;
import za.ac.cput.domain.User;
import za.ac.cput.factory.CartItemFactory;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
class CartItemServiceTest {
    @Autowired

    private ICartItemService cartItemService;

    private static final User user = new User.Builder()
            .setFirstName("Thando")
            .setLastName("Mseleku")
            //.setEmail("123@gmail.com")
            .setPassword("password123")
            .build();


    private static final Cart cart = new Cart.Builder()
            .setUserID(user)
            .build();

    private static final Product product = new Product.Builder()
            .setProductID(1L)
            .setTitle("Digital Art")
            .setDescription("A beautiful digital painting")
            .setPrice(49.99)
            .setCategoryID("C001")
            .build();



    private static final CartItem cartItem1 = CartItemFactory
            .createCartItem(cart,product,user,10,10.000);



    @Test
    void a_create() {
        CartItem newCartItem = cartItemService.create(cartItem1);
        assertNotNull(newCartItem);
        System.out.println(newCartItem);
    }

    @Test
    void b_read() {

        CartItem read = cartItemService.read(cartItem1.getCartItemID());
        assertNotNull(read);
        System.out.println(read);
    }

    @Test
    void c_update() {
        CartItem newCartItem = new CartItem.Builder().copy(cartItem1)
                .setQuantity(50)
                .build();
        CartItem updated = cartItemService.update(newCartItem);
        assertNotNull(updated);
        System.out.println(updated);
    }

    //delete ADDDDDD

    @Test
    void d_getAll() {
        System.out.println(cartItemService.getAll());

    }
}