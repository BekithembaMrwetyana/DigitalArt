package za.ac.cput.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.domain.Inventory;
import za.ac.cput.domain.Product;
import za.ac.cput.repository.ProductRepository;
import za.ac.cput.service.InventoryService;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/inventory")
public class InventoryController {

    private final InventoryService service;
    private final ProductRepository productRepository;

    @Autowired
    public InventoryController(InventoryService service, ProductRepository productRepository) {
        this.service = service;
        this.productRepository = productRepository;
    }

    @PostMapping("/create")
    public Inventory create(@RequestBody Inventory inventory) {
        // Fetch existing product
        Product existingProduct = productRepository.findById(inventory.getProduct().getProductID())
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + inventory.getProduct().getProductID()));

        // Build new inventory without quantity
        Inventory newInventory = new Inventory.Builder()
                .setProduct(existingProduct)
                .build();

        return service.create(newInventory);
    }

    @GetMapping("/read/{id}")
    public Inventory read(@PathVariable Long id) {
        return service.read(id);
    }

    @PutMapping("/update/{id}")
    public Inventory update(@PathVariable Long id, @RequestBody Inventory inventory) {
        Inventory updatedInventory = new Inventory.Builder()
                .copy(inventory)
                .setInventoryID(id)
                .build();

        return service.update(updatedInventory);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Inventory>> getAll() {
        List<Inventory> inventories = service.getAll();
        return new ResponseEntity<>(inventories, HttpStatus.OK);
    }
}
