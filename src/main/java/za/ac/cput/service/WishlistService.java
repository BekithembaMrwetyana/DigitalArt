package za.ac.cput.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ac.cput.domain.Product;
import za.ac.cput.domain.User;
import za.ac.cput.domain.Wishlist;
import za.ac.cput.factory.WishlistFactory;
import za.ac.cput.repository.ProductRepository;
import za.ac.cput.repository.UserRepository;
import za.ac.cput.repository.WishlistRepository;

import java.util.List;
import java.util.Optional;

@Service
public class WishlistService {

    private final WishlistRepository wishlistRepository;

    @Autowired
    public WishlistService(WishlistRepository wishlistRepository) {
        this.wishlistRepository = wishlistRepository;
    }

    public List<Wishlist> getWishlistByUser(User user) {
        return wishlistRepository.findByUser(user);
    }

    public Wishlist addWishlistItem(User user, Product product) {

        List<Wishlist> existing = wishlistRepository.findByUserAndProduct(user, product);
        if (!existing.isEmpty()) {
            return existing.get(0);
        }
        Wishlist wishlist = WishlistFactory.createWishlist(user, product);
        return wishlistRepository.save(wishlist);
    }

    public void removeWishlistItem(User user, Product product) {
        wishlistRepository.deleteByUserAndProduct(user, product);
    }

    public Optional<Wishlist> getWishlistItem(Long id) {
        return wishlistRepository.findById(id);
    }
}
