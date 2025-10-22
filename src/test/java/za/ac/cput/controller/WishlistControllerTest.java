package za.ac.cput.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import za.ac.cput.service.WishlistService;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class WishlistControllerTest {

    private MockMvc mockMvc;

    @Mock
    private WishlistService wishlistService;

    @InjectMocks
    private WishlistController wishlistController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(wishlistController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testAddToWishlist() throws Exception {
        Map<String, Long> request = new HashMap<>();
        request.put("userId", 1L);
        request.put("productId", 100L);

        mockMvc.perform(post("/api/wishlist/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string("Added to wishlist"));

        verify(wishlistService, times(1)).addToWishlist(1L, 100L);
    }

    @Test
    void testAddToWishlistBadRequest() throws Exception {
        Map<String, Long> request = new HashMap<>();
        // Missing productId

        mockMvc.perform(post("/api/wishlist/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("userId and productId are required"));
    }

    @Test
    void testRemoveFromWishlist() throws Exception {
        mockMvc.perform(delete("/api/wishlist/remove/100")
                        .param("userId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Removed from wishlist"));

        verify(wishlistService, times(1)).removeFromWishlist(1L, 100L);
    }

    @Test
    void testGetWishlist() throws Exception {
        List<Long> productIds = Arrays.asList(100L, 200L);
        when(wishlistService.getWishlistProductIdsByUserId(1L)).thenReturn(productIds);

        mockMvc.perform(get("/api/wishlist/1"))
                .andExpect(status().isOk())
                .andExpect(content().json("[100,200]"));

        verify(wishlistService, times(1)).getWishlistProductIdsByUserId(1L);
    }
}
