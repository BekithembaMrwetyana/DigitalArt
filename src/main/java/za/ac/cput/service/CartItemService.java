
package za.ac.cput.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ac.cput.domain.CartItem;
import za.ac.cput.domain.Product;
import za.ac.cput.domain.User;
import za.ac.cput.dto.CartItemDTO;
import za.ac.cput.repository.CartItemRepository;
import za.ac.cput.repository.ProductRepository;
import za.ac.cput.repository.UserRepository;

import java.util.Base64;
import java.util.List;

@Service
public class CartItemService implements ICartItemService {


    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Autowired
    public CartItemService(CartItemRepository cartItemRepository,
                           UserRepository userRepository,
                           ProductRepository productRepository) {
        this.cartItemRepository = cartItemRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }


    @Override
    public CartItem create(CartItem cartItem) {
        return cartItemRepository.save(cartItem);
    }
//    @Override
//    public CartItem create(CartItem cartItem) {
//        Long userId = cartItem.getUser().getUserId();
//        Long productId = cartItem.getProduct().getProductID();
//
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new IllegalArgumentException("User not found"));
//        Product product = productRepository.findById(productId)
//                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
//
//        // check if product already in cart
//        CartItem existing = cartItemRepository.findByUser_UserIdAndProduct_ProductID(userId, productId)
//                .orElse(null);
//
//        if (existing != null) {
//            CartItem updated = new CartItem.Builder()
//                    .copy(existing)
//                    .setQuantity(existing.getQuantity() + cartItem.getQuantity())
//                    .setPrice(product.getPrice())
//                    .build();
//            return cartItemRepository.save(updated);
//        }
//
//        CartItem newItem = new CartItem.Builder()
//                .copy(cartItem)
//                .setUser(user)
//                .setProduct(product)
//                .setPrice(product.getPrice())
//                .build();
//
//        return cartItemRepository.save(newItem);
//    }
public CartItem createFromDTO(CartItemDTO dto) {
    User user = userRepository.findById(dto.getUserId())
            .orElseThrow(() -> new RuntimeException("User not found"));
    Product product = productRepository.findById(dto.getProductId())
            .orElseThrow(() -> new RuntimeException("Product not found"));

    CartItem cartItem = new CartItem.Builder()
            .setUser(user)
            .setProduct(product)
            .setQuantity(dto.getQuantity())
            .setPrice(dto.getPrice())
            .build();

    return cartItemRepository.save(cartItem);
}


    @Override
    public CartItem read(Long cartItemId) {
        return cartItemRepository.findById(cartItemId).orElse(null);
    }

    @Override
    public CartItem update(CartItem cartItem) {
        throw new UnsupportedOperationException("Cart items cannot be updated. Remove and re-add instead.");
    }

    @Override
    public void delete(Long cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }

    @Override
    public List<CartItem> getAll() {
        return cartItemRepository.findAll();
    }

    private void encodeImage(Product product) {
        if (product.getImageData() != null) {
            product.setImageBase64(Base64.getEncoder().encodeToString(product.getImageData()));
        } else {
            product.setImageBase64(null);
        }
    }

    public List<CartItem> findByUserId(Long userId) {
        List<CartItem> items = cartItemRepository.findByUser_UserId(userId);
        // encode the images before returning
        items.forEach(item -> encodeImage(item.getProduct()));
        return items;
    }

}
