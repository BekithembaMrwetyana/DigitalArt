package za.ac.cput.controller;

import za.ac.cput.domain.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.service.CartService;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/cart")

public class CartController {
    private CartService service;

    @Autowired
    public CartController(CartService service){
        this.service = service;
    }

    @PostMapping("/create")
    public Cart create(@RequestBody Cart cart){
        return service.create(cart);
    }
    @GetMapping("/read/{cartID}")
    public Cart read(@PathVariable Long cartID){
        return service.read(cartID);
    }
    @PutMapping("/update")
    public Cart update(@RequestBody Cart cart){
        return service.update(cart);
    }
    @DeleteMapping("/delete/{cartID}")
    public void delete(@PathVariable Long cartID){
        this.service.delete(cartID);
    }
    @GetMapping("/getAll")
    public List<Cart> getAll(){
        return service.getAll();
    }
}
