package za.ac.cput.factory;

import za.ac.cput.domain.Wishlist;
import za.ac.cput.domain.Product;
import za.ac.cput.domain.User;


import java.time.LocalDateTime;
import java.util.List;

public class WishlistFactory {

    public static Wishlist createWishlist(User user, Product product) {
        return new Wishlist.Builder()
                .setUser(user)
                .setProduct(product)
                .setDateAdded(LocalDateTime.now())
                .build();
    }

    public static Wishlist createWishlist(Long id, User user, Product product, LocalDateTime dateAdded) {
        return new Wishlist.Builder()
                .setId(id)
                .setUser(user)
                .setProduct(product)
                .setDateAdded(dateAdded)
                .build();
    }
}
