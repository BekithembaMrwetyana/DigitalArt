package za.ac.cput.service;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.ac.cput.domain.Product;
import za.ac.cput.domain.User;
import za.ac.cput.domain.Wishlist;
import za.ac.cput.domain.enums.Role;
import za.ac.cput.factory.WishlistFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
class WishlistServiceTest {

    @Autowired
    private IWishlistService service;

    static User user = new User.Builder()
            .setUserId(1L)
            .setFirstName("John")
            .setLastName("Doe")
            .setPassword("password123")
            .setRole(Role.CUSTOMER)
            .setCreateDate(LocalDate.now())
            .setLastLogin(LocalDateTime.now())
            .build();

    static Product product = new Product.Builder()
            .setProductID(123L)
            .setTitle("Sample Product")
            .setDescription("Description")
            .setPrice(50.0)
            .build();

    private final Wishlist wishlist;

    WishlistServiceTest(IWishlistService service) {
        this.service = service;
        List<Product> products = new ArrayList<>();
        products.add(product);
        this.wishlist = WishlistFactory.createWishlist(1L, user, products);

    }

    @Test
    void a_create() {
        Wishlist created = service.create(wishlist);
        assertNotNull(created);
        assertEquals(user.getUserId(), created.getUser().getUserId());
        System.out.println("Created: " + created);
    }

    @Test
    void b_read() {
        Wishlist read = service.read(wishlist.getWishlistID());
        assertNotNull(read);
        assertEquals(wishlist.getWishlistID(), read.getWishlistID());
        System.out.println("Read: " + read);
    }

    @Test
    void c_update() {
        List<Product> updatedProducts = new ArrayList<>();
        Product newProduct = new Product.Builder()
                .setProductID(123L)
                .setTitle("Updated Product")
                .setDescription("Updated Description")
                .setPrice(75.0)

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
    void d_getAll() {
        List<Wishlist> all = service.getAll();
        assertNotNull(all);
        System.out.println("All wishlists: " + all);
    }

    @Test
    void e_delete() {
        service.delete(wishlist.getWishlistID());
        Wishlist deleted = service.read(wishlist.getWishlistID());
        assertNull(deleted);
        System.out.println("Deleted wishlist with ID: " + wishlist.getWishlistID());
    }
}
