
package za.ac.cput.factory;

import org.junit.jupiter.api.Test;
import za.ac.cput.domain.Wishlist;
import static org.junit.jupiter.api.Assertions.*;


class WishlistFactoryTest {

    @Test
    void testCreateWishlist() {
        Long userId = 1L;
        Long productId = 100L;

        Wishlist entity = WishlistFactory.createWishlist(userId, productId);

        assertNotNull(entity);
        assertEquals(userId, entity.getUserId());
        assertEquals(productId, entity.getProductId());
        assertNull(entity.getId());
    }
}