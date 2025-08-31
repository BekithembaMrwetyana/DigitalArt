package za.ac.cput.service;
/*
CartService.java
Cart service
Author: Bekithemba Mrwetyana (222706066)
Date: 06 July 2025
*/
import org.springframework.beans.factory.annotation.Autowired;
import za.ac.cput.domain.Cart;
import org.springframework.stereotype.Service;
import za.ac.cput.repository.CartRepository;

import java.util.List;

@Service
public class CartService implements ICartService{

    private final CartRepository repository;

    @Autowired   // ✅ constructor injection recommended
    public CartService(CartRepository repository) {
        this.repository = repository;
    }

    public Cart create(Cart cart) {
        return repository.save(cart);
    }

    public Cart read(Long cartID) {
        return repository.findById(cartID).orElse(null);
    }

    public Cart update(Cart cart) {
        return repository.save(cart);
    }

    public void delete(Long cartID) {  //use void
        repository.deleteById(cartID);
    }

    public List<Cart> getAll(){
        return repository.findAll();
    }
}
