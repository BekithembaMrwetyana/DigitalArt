package za.ac.cput.factory;

import za.ac.cput.domain.Wishlist;
import za.ac.cput.domain.Product;
import za.ac.cput.domain.User;


import java.time.LocalDateTime;
import java.util.List;

public class WishlistFactory {

    public static Wishlist createWishlist(Long userId, Long productId) {
        return new Wishlist(userId, productId);
    }
}