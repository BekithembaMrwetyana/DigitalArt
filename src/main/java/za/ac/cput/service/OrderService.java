package za.ac.cput.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ac.cput.domain.CartItem;
import za.ac.cput.domain.Order;
import za.ac.cput.domain.User;
import za.ac.cput.domain.enums.OrderStatus;
import za.ac.cput.dto.OrderDTO;
import za.ac.cput.repository.CartItemRepository;
import za.ac.cput.repository.OrderRepository;
import za.ac.cput.repository.UserRepository;
import za.ac.cput.repository.ProductRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService implements IOrderService {

    private final OrderRepository repository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    private final CartItemRepository cartItemRepository;

    @Autowired
    public OrderService(OrderRepository repository,
                        UserRepository userRepository,
                        ProductRepository productRepository,CartItemRepository cartItemRepository ) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.cartItemRepository = cartItemRepository;
    }

    /**
     * Create order from DTO (used by frontend)
     */
    public Order createOrderFromDTO(OrderDTO dto) {
        // Fetch the user
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Handle Cart Items (avoid duplicates)
        List<CartItem> items = dto.getCartItems().stream().map(ci -> {
            // Check if this product already exists for this user
            return cartItemRepository.findByUserUserIdAndProductProductID(user.getUserId(), ci.getProductId())
                    .map(existing -> {
                        // Update quantity & price
                        existing.setQuantity(existing.getQuantity() + ci.getQuantity());
                        existing.setPrice(ci.getPrice()); // optional: keep latest price
                        return existing;
                    })
                    .orElseGet(() -> new CartItem.Builder()
                            .setQuantity(ci.getQuantity())
                            .setPrice(ci.getPrice())
                            .setProduct(
                                    productRepository.findById(ci.getProductId())
                                            .orElseThrow(() -> new RuntimeException("Product not found"))
                            )
                            .setUser(user)
                            .build());
        }).toList();

        // Build the order using Order.Builder
        Order order = new Order.Builder()
                .setCartItem(items)
                .setTotalAmount(dto.getTotalAmount())
                .setOrderAmount(dto.getOrderAmount())
                .setOrderDate(LocalDateTime.now())
                .build();

        order.setUser(user);
        order.setPaymentStatus(OrderStatus.PENDING);

        // Link items back to order
        items.forEach(item -> item.setOrder(order));
        order.setCartItems(items);

        // Save and return the order
        return repository.save(order);
    }



    @Override
    public Order create(Order order) {
        return repository.save(order);
    }

    @Override
    public Order read(Long orderID) {
        return repository.findById(orderID).orElse(null);
    }

    @Override
    public Order update(Order order) {
        return repository.save(order);
    }

    /**
     * Update only the payment status
     */
    public Order updateStatus(Long id, OrderStatus status) {
        Order order = repository.findById(id).orElse(null);
        if (order == null) return null;
        order.setPaymentStatus(status);
        return repository.save(order);
    }

    @Override
    public void delete(Long orderID) {
        repository.deleteById(orderID);
    }

    @Override
    public List<Order> getAllOrders() {
        return repository.findAll();
    }

    @Override
    public List<Order> getAll() {
        return repository.findAll();
    }

    /**
     * Get all orders for a specific user
     */
    public List<Order> getOrdersByUserId(Long userId) {
        return repository.findByUserUserId(userId);
    }

    /**
     * Get orders for a user filtered by status
     */
    public List<Order> getOrdersByUserIdAndStatus(Long userId, OrderStatus status) {
        return repository.findByUserUserIdAndPaymentStatus(userId, status);
    }
}
