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
import java.util.stream.Collectors;

@Service
public class WishlistService {

    @Autowired
    private WishlistRepository wishlistRepository;

    public void addToWishlist(Long userId, Long productId) {
        if (!wishlistRepository.existsByUserIdAndProductId(userId, productId)) {
            Wishlist wishlist = WishlistFactory.createWishlist(userId, productId);
            wishlistRepository.save(wishlist);
        }
    }

    public void removeFromWishlist(Long userId, Long productId) {
        wishlistRepository.deleteByUserIdAndProductId(userId, productId);
    }

    public List<Long> getWishlistProductIdsByUserId(Long userId) {
        List<Wishlist> wishlistEntities = wishlistRepository.findByUserId(userId);
        return wishlistEntities.stream()
                .map(Wishlist::getProductId)
                .collect(Collectors.toList());
    }
}
