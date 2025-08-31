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

        User user = cartItem.getUser();
        if (user != null && user.getUserId() == null) {
            user = userRepository.save(user);
        }


        Product product = cartItem.getProduct();
        if (product != null) {
            Category category = product.getCategory();
            if (category != null && category.getCategoryId() == null) {
                category = categoryRepository.save(category);
                product = new Product.Builder()
                        .copy(product)
                        .setCategory(category)
                        .build();
            }

            if (product.getProductID() == null) {
                product = productRepository.save(product);
            }
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
}
