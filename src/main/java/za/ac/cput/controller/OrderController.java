package za.ac.cput.controller;

import org.springframework.http.ResponseEntity;
import za.ac.cput.domain.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.service.OrderService;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService service;

    @Autowired
    public OrderController(OrderService service) {
        this.service = service;
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
    @PutMapping("/update/{id}")
    public ResponseEntity<Order> update(@PathVariable Long id, @RequestBody Order order) {
        order.setId(id);
        Order updated = service.update(order);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    // Delete
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
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
    @GetMapping("/test")
    public ResponseEntity<List<Order>> testOrders() {
        List<Order> orders = service.getOrdersByUserId(10L);
        return ResponseEntity.ok(orders);
    }

}
