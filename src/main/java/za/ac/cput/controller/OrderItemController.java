package za.ac.cput.controller;

import za.ac.cput.domain.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.service.OrderItemService;

import java.util.List;

@RestController
@RequestMapping("/orderitems")
@CrossOrigin(origins = "http://localhost:5173")
public class OrderItemController {

    private final OrderItemService service;

    @Autowired
    public OrderItemController(OrderItemService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public OrderItem create(@RequestBody OrderItem orderItem) {
        return service.create(orderItem);
    }

    @GetMapping("/read/{id}")
    public OrderItem read(@PathVariable Long id) {
        return service.read(id);
    }

    @PutMapping("/update")
    public OrderItem update(@RequestBody OrderItem orderItem) {
        return service.update(orderItem);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @GetMapping("/getAll")
    public List<OrderItem> getAll() {
        return service.getAll();
    }

    @GetMapping("/order/{orderID}")
    public List<OrderItem> getByOrderID(@PathVariable Long orderID) {
        return service.getByOrderID(orderID);
    }

    @GetMapping("/product/{productID}")
    public List<OrderItem> getByProductID(@PathVariable Long productID) {
        return service.getByProductID(productID);
    }

    @GetMapping("/quantityAbove")
    public List<OrderItem> filterByQuantityGreaterThan(@RequestParam int quantity) {
        return service.filterByQuantityGreaterThan(quantity);
    }

    @GetMapping("/subTotalRange")
    public List<OrderItem> filterBySubTotalBetween(@RequestParam double min, @RequestParam double max) {
        return service.filterBySubTotalBetween(min, max);
    }
}
