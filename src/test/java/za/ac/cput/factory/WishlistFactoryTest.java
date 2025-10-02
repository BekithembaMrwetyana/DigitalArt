
package za.ac.cput.factory;

import org.junit.jupiter.api.Test;
import za.ac.cput.domain.Wishlist;
import za.ac.cput.domain.User;
import za.ac.cput.domain.Product;

import java.time.LocalDateTime;


import static org.junit.jupiter.api.Assertions.*;


class WishlistFactoryTest {

    @Test
    void testCreateWishlist() {

        User user = new User.Builder()
                .setUserId(1L)
                .setFirstName("John")
                .setLastName("Doe")
                .setEmail("john@example.com")
                .build();

        Product product = new Product.Builder()
                .setProductID(1L)
                .setTitle("Test Product")
                .setPrice(100.0)
                .build();


        Wishlist wishlist = WishlistFactory.createWishlist(user, product);


        assertNotNull(wishlist);
        assertEquals(user, wishlist.getUser());
        assertEquals(product, wishlist.getProduct());
        assertNotNull(wishlist.getDateAdded());
        assertTrue(wishlist.getDateAdded().isBefore(LocalDateTime.now().plusSeconds(1)));
    }

    @Test
    void testCreateWishlistWithIdAndDate() {
        // Given
        User user = new User.Builder().setUserId(1L).build();
        Product product = new Product.Builder().setProductID(1L).build();
        LocalDateTime dateAdded = LocalDateTime.now().minusDays(1);


        Wishlist wishlist = WishlistFactory.createWishlist(1L, user, product, dateAdded);


        assertNotNull(wishlist);
        assertEquals(1L, wishlist.getId());
        assertEquals(user, wishlist.getUser());
        assertEquals(product, wishlist.getProduct());
        assertEquals(dateAdded, wishlist.getDateAdded());
    }
}