package za.ac.cput.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import za.ac.cput.domain.Wishlist;

import za.ac.cput.repository.WishlistRepository;

import java.util.Arrays;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class WishlistServiceTest {

    @Mock
    private WishlistRepository wishlistRepository;

    @InjectMocks
    private WishlistService wishlistService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddToWishlist() {
        Long userId = 1L;
        Long productId = 100L;

        when(wishlistRepository.existsByUserIdAndProductId(userId, productId)).thenReturn(false);

        wishlistService.addToWishlist(userId, productId);

        verify(wishlistRepository, times(1)).existsByUserIdAndProductId(userId, productId);
        verify(wishlistRepository, times(1)).save(any(Wishlist.class));
    }

    @Test
    void testAddToWishlistAlreadyExists() {
        Long userId = 1L;
        Long productId = 100L;

        when(wishlistRepository.existsByUserIdAndProductId(userId, productId)).thenReturn(true);

        wishlistService.addToWishlist(userId, productId);

        verify(wishlistRepository, times(1)).existsByUserIdAndProductId(userId, productId);
        verify(wishlistRepository, never()).save(any(Wishlist.class));
    }

    @Test
    void testRemoveFromWishlist() {
        Long userId = 1L;
        Long productId = 100L;

        wishlistService.removeFromWishlist(userId, productId);

        verify(wishlistRepository, times(1)).deleteByUserIdAndProductId(userId, productId);
    }

    @Test
    void testGetWishlistProductIdsByUserId() {
        Long userId = 1L;
        Wishlist entity1 = new Wishlist(userId, 100L);
        Wishlist entity2 = new Wishlist(userId, 200L);
        List<Wishlist> entities = Arrays.asList(entity1, entity2);

        when(wishlistRepository.findByUserId(userId)).thenReturn(entities);

        List<Long> productIds = wishlistService.getWishlistProductIdsByUserId(userId);

        assertEquals(2, productIds.size());
        assertTrue(productIds.contains(100L));
        assertTrue(productIds.contains(200L));
        verify(wishlistRepository, times(1)).findByUserId(userId);
    }
}
