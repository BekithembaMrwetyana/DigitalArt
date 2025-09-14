package za.ac.cput.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.domain.Inventory;
import za.ac.cput.service.InventoryService;

import java.util.List;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    private final InventoryService service;

    @Autowired
    public InventoryController(InventoryService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public Inventory create(@RequestBody Inventory inventory) {
        return service.create(inventory);
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
    public ResponseEntity<List <Inventory>> getAll() {
        List<Inventory> inventories = service.getAll();
        return new ResponseEntity<>(inventories, HttpStatus.OK);
    }
}
