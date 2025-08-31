package za.ac.cput.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.ac.cput.domain.Product;
import za.ac.cput.domain.User;
import za.ac.cput.domain.Wishlist;
import za.ac.cput.factory.WishlistFactory;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
class WishlistServiceTest {

    @Autowired
    private IWishlistService service;

    private static Wishlist wishlist;

    private static User user;
    private static Product product;

    @BeforeAll
    static void setup() {
        user = new User.Builder()
                .setUserId(1L)
                .setFirstName("John")
                .setLastName("Doe")
                .build();

        product = new Product.Builder()
                .setProductID(123L)
                .setTitle("Sample Product")
                .setDescription("Description")
                .setPrice(50.0)
                .setCategoryID("C1")
                .build();

        List<Product> products = new ArrayList<>();
        products.add(product);

        wishlist = WishlistFactory.createWishlist(1L, user, products);
    }

    @Test
    @Order(1)
    void a_create() {
        Wishlist created = service.create(wishlist);
        assertNotNull(created);
        System.out.println("Created: " + created);
    }

    @Test
    @Order(2)
    void b_read() {
        Wishlist read = service.read(wishlist.getWishlistID());
        assertNotNull(read);
        System.out.println("Read: " + read);
    }

    @Test
    @Order(3)
    void c_update() {
        List<Product> updatedProducts = new ArrayList<>();
        Product newProduct = new Product.Builder()
                .setProductID(124L)
                .setTitle("Updated Product")
                .setDescription("Updated Description")
                .setPrice(75.0)
                .setCategoryID("C2")
                .build();
        updatedProducts.add(newProduct);

        Wishlist updatedWishlist = new Wishlist.Builder()
                .copy(wishlist)
                .setProducts(updatedProducts)
                .build();

        Wishlist updated = service.update(updatedWishlist);
        assertNotNull(updated);
        assertEquals(1, updated.getProducts().size());
        System.out.println("Updated: " + updated);
    }

    @Test
    @Order(4)
    void d_getAll() {
        List<Wishlist> all = service.getAll();
        assertNotNull(all);
        System.out.println("All wishlists: " + all);
    }

    @Test
    @Order(5)
    void e_delete() {
        service.delete(wishlist.getWishlistID());
        Wishlist deleted = service.read(wishlist.getWishlistID());
        assertNull(deleted);
        System.out.println("Deleted wishlist with ID: " + wishlist.getWishlistID());
    }
}
