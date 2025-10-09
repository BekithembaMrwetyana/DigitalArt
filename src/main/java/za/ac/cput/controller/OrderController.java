package za.ac.cput.controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import za.ac.cput.domain.CartItem;
import za.ac.cput.domain.Order;
import za.ac.cput.dto.OrderDTO;
import za.ac.cput.domain.enums.OrderStatus;
import za.ac.cput.repository.CartItemRepository;
import za.ac.cput.service.OrderService;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService service;
    private final CartItemRepository cartItemRepository;

    @Autowired
    public OrderController(OrderService service, CartItemRepository cartItemRepository) {
        this.service = service;
        this.cartItemRepository = cartItemRepository;
        System.out.println("✅ OrderController loaded");
    }

    // 🟢 Create Order
    @PostMapping("/create")
    public ResponseEntity<Order> create(@RequestBody OrderDTO orderDTO) {
        try {
            Order saved = service.createOrderFromDTO(orderDTO);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    // 🟢 Read Order
    @GetMapping("/read/{id}")
    public ResponseEntity<Order> read(@PathVariable Long id) {
        Order order = service.read(id);
        return order != null ? ResponseEntity.ok(order) : ResponseEntity.notFound().build();
    }

    // 🟡 Update Order Status
    @PutMapping("/updateStatus/{id}")
    public ResponseEntity<Map<String, Object>> updateStatus(
            @PathVariable Long id,
            @RequestBody Map<String, String> updates) {

        String statusStr = updates.get("paymentStatus");
        OrderStatus status;

        try {
            status = OrderStatus.valueOf(statusStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid payment status value"));
        }

        Order updated = service.updateStatus(id, status);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }

        Map<String, Object> response = new HashMap<>();
        response.put("orderId", updated.getOrderID());
        response.put("paymentStatus", updated.getPaymentStatus().name());
        response.put("message", "Order status updated successfully.");

        // Only include download links when order is completed
        if (status == OrderStatus.COMPLETED) {
            response.put("downloadLinks", service.generateDownloadLinks(updated));
        }

        return ResponseEntity.ok(response);
    }

    // 🟢 Download artwork for a specific product in an order
    @GetMapping("/download/{orderId}/{productId}")
    public ResponseEntity<Resource> downloadArt(
            @PathVariable Long orderId,
            @PathVariable Long productId) {
        // ✅ Fetch the specific cart item
        CartItem item = cartItemRepository.findByOrderOrderIDAndProductProductID(orderId, productId)
                .orElseThrow(() -> new RuntimeException("Item not found"));

        // ✅ Get image path from the product entity
        String imagePath = item.getProduct().getImageUrl();

        if (imagePath == null || imagePath.isEmpty()) {
            throw new RuntimeException("No image URL for product ID: " + productId);
        }

        // ✅ Normalize the path (remove leading slashes if necessary)
        if (imagePath.startsWith("/")) {
            imagePath = imagePath.substring(1);
        }

        try {
            // ✅ Build absolute path to your static images
            Path filePath = Paths.get("src/main/resources/static/", imagePath).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (!resource.exists()) {
                throw new RuntimeException("File not found: " + imagePath);
            }

            // ✅ Use the product title in the download filename
            String fileName = item.getProduct().getTitle().replaceAll("\\s+", "_") + ".jpeg";

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(resource);

        } catch (Exception e) {
            throw new RuntimeException("Error downloading image: " + e.getMessage());
        }
    }





    // 🗑️ Delete
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

    // 🟢 Get all
    @GetMapping("/getAll")
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = service.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    // 🟢 Get orders by user
    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Order>> getOrdersByUserId(@PathVariable Long userId) {
        List<Order> orders = service.getOrdersByUserId(userId);
        return ResponseEntity.ok(orders);
    }

    // 🟢 Get orders by user + status
    @GetMapping("/user/{userId}/status/{status}")
    public ResponseEntity<List<Order>> getOrdersByUserIdAndStatus(
            @PathVariable Long userId,
            @PathVariable String status) {
        try {
            OrderStatus orderStatus = OrderStatus.valueOf(status.toUpperCase());
            List<Order> orders = service.getOrdersByUserIdAndStatus(userId, orderStatus);
            return ResponseEntity.ok(orders);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Test endpoint
    @GetMapping("/test")
    public ResponseEntity<List<Order>> testOrders() {
        List<Order> orders = service.getOrdersByUserId(10L);
        return ResponseEntity.ok(orders);
    }
}
