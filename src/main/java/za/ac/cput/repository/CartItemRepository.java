package za.ac.cput.repository;
/*
CartItemRepository.java
CartItemRepository POJO class
Author: Thandolwethu P Mseleku
Date: 25/05/2025
*/
import org.springframework.data.repository.query.Param;
import za.ac.cput.domain.Cart;
import za.ac.cput.domain.CartItem;
import za.ac.cput.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    List<CartItem> findByUser_UserId(Long userId);
    //List<CartItem> findByUserIdWithProducts(@Param("userId") Long userId);


    List<CartItem> findByCart(Cart cart);

    List<CartItem>findByProduct(Product product);

    List<CartItem> findByQuantityGreaterThan(int quantity);
}
