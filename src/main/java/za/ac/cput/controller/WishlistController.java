package za.ac.cput.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.service.WishlistService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/wishlist")
@CrossOrigin(origins = "http://localhost:5173")
public class WishlistController {

    @Autowired
    private WishlistService wishlistService;

    @PostMapping("/add")
    public ResponseEntity<String> addToWishlist(@RequestBody Map<String, Long> request) {
        Long userId = request.get("userId");
        Long productId = request.get("productId");
        if (userId == null || productId == null) {
            return ResponseEntity.badRequest().body("userId and productId are required");
        }
        wishlistService.addToWishlist(userId, productId);
        return ResponseEntity.ok("Added to wishlist");
    }

    @DeleteMapping("/remove/{productId}")
    public ResponseEntity<String> removeFromWishlist(@PathVariable Long productId, @RequestParam Long userId) {
        wishlistService.removeFromWishlist(userId, productId);
        return ResponseEntity.ok("Removed from wishlist");
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Long>> getWishlist(@PathVariable Long userId) {
        List<Long> productIds = wishlistService.getWishlistProductIdsByUserId(userId);
        return ResponseEntity.ok(productIds);
    }
}
