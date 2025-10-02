package za.ac.cput.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import za.ac.cput.domain.Product;
import za.ac.cput.domain.User;
import za.ac.cput.domain.Wishlist;
import za.ac.cput.factory.WishlistFactory;
import za.ac.cput.repository.WishlistRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class WishlistServiceTest {

    @Mock
    private WishlistRepository wishlistRepository;

    @InjectMocks
    private WishlistService wishlistService;

    private User user;
    private Product product;
    private Wishlist wishlist;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User.Builder()
                .setUserId(1L)
                .setFirstName("John")
                .setLastName("Doe")
                .build();

        product = new Product.Builder()
                .setProductID(1L)
                .setTitle("Test Product")
                .build();

        wishlist = WishlistFactory.createWishlist(user, product);
        wishlist.setId(1L);
    }

    @Test
    void testGetWishlistByUser() {
        // Given
        when(wishlistRepository.findByUser(user)).thenReturn(Arrays.asList(wishlist));

        // When
        List<Wishlist> result = wishlistService.getWishlistByUser(user);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(wishlist, result.get(0));
        verify(wishlistRepository, times(1)).findByUser(user);
    }

    @Test
    void testAddWishlistItem_NewItem() {

        when(wishlistRepository.findByUserAndProduct(user, product)).thenReturn(Arrays.asList());
        when(wishlistRepository.save(any(Wishlist.class))).thenReturn(wishlist);


        Wishlist result = wishlistService.addWishlistItem(user, product);


        assertNotNull(result);
        assertEquals(wishlist, result);
        verify(wishlistRepository, times(1)).findByUserAndProduct(user, product);
        verify(wishlistRepository, times(1)).save(any(Wishlist.class));
    }

    @Test
    void testAddWishlistItem_ExistingItem() {

        when(wishlistRepository.findByUserAndProduct(user, product)).thenReturn(Arrays.asList(wishlist));


        Wishlist result = wishlistService.addWishlistItem(user, product);


        assertNotNull(result);
        assertEquals(wishlist, result);
        verify(wishlistRepository, times(1)).findByUserAndProduct(user, product);
        verify(wishlistRepository, never()).save(any(Wishlist.class));
    }

    @Test
    void testRemoveWishlistItem() {

        wishlistService.removeWishlistItem(user, product);


        verify(wishlistRepository, times(1)).deleteByUserAndProduct(user, product);
    }

    @Test
    void testGetWishlistItem() {

        when(wishlistRepository.findById(1L)).thenReturn(Optional.of(wishlist));


        Optional<Wishlist> result = wishlistService.getWishlistItem(1L);


        assertTrue(result.isPresent());
        assertEquals(wishlist, result.get());
        verify(wishlistRepository, times(1)).findById(1L);
    }
}
