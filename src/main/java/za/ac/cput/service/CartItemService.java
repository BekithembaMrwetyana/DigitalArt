
package za.ac.cput.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ac.cput.domain.CartItem;
import za.ac.cput.domain.Category;
import za.ac.cput.domain.Product;
import za.ac.cput.domain.User;
import za.ac.cput.repository.CartItemRepository;
import za.ac.cput.repository.CategoryRepository;
import za.ac.cput.repository.ProductRepository;
import za.ac.cput.repository.UserRepository;

import java.util.List;

@Service
public class CartItemService implements ICartItemService {

    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public CartItemService(CartItemRepository cartItemRepository,
                           UserRepository userRepository,
                           ProductRepository productRepository,
                           CategoryRepository categoryRepository) {
        this.cartItemRepository = cartItemRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public CartItem create(CartItem cartItem) {
        Long userId = cartItem.getUser() != null ? cartItem.getUser().getUserId() : null;
        if (userId == null) {
            throw new IllegalArgumentException("User ID is required");
        }
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Product product = cartItem.getProduct();
        if (product != null && product.getProductID() != null) {
            product = productRepository.findById(product.getProductID())
                    .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        }

        CartItem savedCartItem = new CartItem.Builder()
                .setCart(cartItem.getCart())
                .setProduct(product)
                .setQuantity(cartItem.getQuantity())
                .setPrice(cartItem.getPrice())
                .setUser(user)
                .build();

        return cartItemRepository.save(savedCartItem);
    }


    @Override
    public CartItem read(Long cartItemId) {
        return cartItemRepository.findById(cartItemId).orElse(null);
    }

    @Override
    public CartItem update(CartItem cartItem) {
        return cartItemRepository.save(cartItem);
    }

    @Override
    public void delete(Long cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }

    @Override
    public List<CartItem> getAll() {
        return cartItemRepository.findAll();
    }

    public List<CartItem> findByUserId(Long userId) {
        return cartItemRepository.findByUser_UserId(userId);
    }

}
