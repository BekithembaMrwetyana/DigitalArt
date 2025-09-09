package za.ac.cput.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.domain.CartItem;
import za.ac.cput.service.CartItemService;
/*
CartItemController.java
CartItem controller
Author: Thandolwethu P MSELEKU(223162477)
Date: 03 August 2025
*/


import java.util.List;

@RestController
@RequestMapping("/cart_item")
public class CartItemController {

    private final CartItemService service;

    @Autowired
    public CartItemController(CartItemService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public CartItem create(@RequestBody CartItem cartItem) {
        return service.create(cartItem);
    }

    @GetMapping("/read/{cartItemId}")
    public CartItem read(@PathVariable Long cartItemId) {
        return service.read(cartItemId);
    }

    @PutMapping("/update")
    public CartItem update(@RequestBody CartItem cartItem) {
        return service.update(cartItem);
    }

    @DeleteMapping("/delete/{cartItemId}")
    public void delete(@PathVariable Long cartItemId) {
        service.delete(cartItemId);
    }

    @GetMapping("/getAll")
    public List<CartItem> getAll() {
        return service.getAll();
    }
    @GetMapping("/findByUser/{userId}")
    public List<CartItem> getCartItemsByUser(@PathVariable Long userId) {
        return service.findByUserId(userId);
    }

}