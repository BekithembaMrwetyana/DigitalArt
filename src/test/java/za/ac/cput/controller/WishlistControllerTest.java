package za.ac.cput.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import za.ac.cput.domain.Product;
import za.ac.cput.domain.User;
import za.ac.cput.domain.Wishlist;
import za.ac.cput.factory.WishlistFactory;
import za.ac.cput.service.WishlistService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(WishlistController.class)
class WishlistControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WishlistService wishlistService;

    @Autowired
    private ObjectMapper objectMapper;

    private User user;
    private Product product;
    private Wishlist wishlist;

    @BeforeEach
    void setUp() {
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
    void testGetWishlistByUser() throws Exception {

        List<Wishlist> wishlists = Arrays.asList(wishlist);
        when(wishlistService.getWishlistByUser(any(User.class))).thenReturn(wishlists);


        mockMvc.perform(get("/api/wishlist/user/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1L));
    }

    @Test
    void testAddWishlistItem() throws Exception {

        when(wishlistService.addWishlistItem(any(User.class), any(Product.class))).thenReturn(wishlist);


        mockMvc.perform(post("/api/wishlist/add")
                        .param("userId", "1")
                        .param("productId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void testRemoveWishlistItem() throws Exception {

        mockMvc.perform(delete("/api/wishlist/remove")
                        .param("userId", "1")
                        .param("productId", "1"))
                .andExpect(status().isNoContent());
    }
}
