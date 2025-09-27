package za.ac.cput.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.domain.CartItem;
import za.ac.cput.dto.CartItemDTO;
import za.ac.cput.service.CartItemService;
/*
CartItemController.java
CartItem controller
Author: Thandolwethu P MSELEKU(223162477)
Date: 03 August 2025
*/


import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
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

    @DeleteMapping("/delete/{cartItemId}")
    public void delete(@PathVariable Long cartItemId) {
        service.delete(cartItemId);
    }

    @GetMapping("/findByUser/{userId}")
    public List<CartItemDTO> getCartItemsByUser(@PathVariable Long userId) {
        return service.findByUserId(userId)
                .stream()
                .map(item -> new CartItemDTO(
                        item.getCartItemID(),
                        item.getProduct(),
                        item.getPrice(),
                        item.getUser().getUserId()
                )).toList();
    }
}
