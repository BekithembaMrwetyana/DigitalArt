package za.ac.cput.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.domain.Product;
import za.ac.cput.domain.User;
import za.ac.cput.domain.Wishlist;
import za.ac.cput.service.WishlistService;

import java.util.List;

@RestController
@RequestMapping("/api/wishlist")
public class WishlistController {

    private final WishlistService wishlistService;

    @Autowired
    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Wishlist>> getWishlistByUser(@PathVariable Long userId) {
        User user = new User.Builder().setUserId(userId).build();
        List<Wishlist> wishlist = wishlistService.getWishlistByUser(user);
        return ResponseEntity.ok(wishlist);
    }

    @PostMapping("/add")
    public ResponseEntity<Wishlist> addWishlistItem(@RequestParam Long userId, @RequestParam Long productId) {
        User user = new User.Builder().setUserId(userId).build();
        Product product = new Product.Builder().setProductID(productId).build();
        Wishlist wishlist = wishlistService.addWishlistItem(user, product);
        return ResponseEntity.ok(wishlist);
    }

    @DeleteMapping("/remove")
    public ResponseEntity<Void> removeWishlistItem(@RequestParam Long userId, @RequestParam Long productId) {
        User user = new User.Builder().setUserId(userId).build();
        Product product = new Product.Builder().setProductID(productId).build();
        wishlistService.removeWishlistItem(user, product);
        return ResponseEntity.noContent().build();
    }
}
