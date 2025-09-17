package za.ac.cput.controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.http.ResponseEntity;
import za.ac.cput.domain.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.service.OrderService;
import za.ac.cput.domain.enums.OrderStatus;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/api/orders")



public class OrderController {

    private final OrderService service;

    @Autowired
    public OrderController(OrderService service) {
        this.service = service;
        System.out.println("OrderController loaded ");
    }

    // Create
    @PostMapping("/create")
    public ResponseEntity<Order> create(@RequestBody Order order) {
        Order saved = service.create(order);
        return ResponseEntity.ok(saved);
    }

    // Read by id
    @GetMapping("/read/{id}")
    public ResponseEntity<Order> read(@PathVariable Long id) {
        Order order = service.read(id);
        return order != null ? ResponseEntity.ok(order) : ResponseEntity.notFound().build();
    }

    // Update
    @PutMapping("/updateStatus/{id}")
    public ResponseEntity<Order> updateStatus(@PathVariable Long id, @RequestBody Map<String, String> updates) {
        String statusStr = updates.get("paymentStatus");
        OrderStatus status;

        try {
            status = OrderStatus.valueOf(statusStr.toUpperCase()); // convert string to enum
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null); // invalid status
        }

        Order updated = service.updateStatus(id, status);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }




    // Delete
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

    // Get all
    @GetMapping("/getAll")
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = service.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    // Get orders by userId
    @CrossOrigin(origins = "http://localhost:5173")
   @GetMapping("/user/{userId}")
   public ResponseEntity<List<Order>> getOrdersByUserId(@PathVariable Long userId) {
       List<Order> orders = service.getOrdersByUserId(userId);
       return ResponseEntity.ok(orders);
  }
    @GetMapping("/user/{userId}/status/{status}")
    public ResponseEntity<List<Order>> getOrdersByUserIdAndStatus(
            @PathVariable Long userId,
            @PathVariable String status) {
        try {
            OrderStatus orderStatus = OrderStatus.valueOf(status.toUpperCase());
            List<Order> orders = service.getOrdersByUserIdAndStatus(userId, orderStatus);
            return ResponseEntity.ok(orders);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build(); // invalid status
        }
    }

    @GetMapping("/test")
    public ResponseEntity<List<Order>> testOrders() {
        List<Order> orders = service.getOrdersByUserId(10L);
        return ResponseEntity.ok(orders);
    }

}
