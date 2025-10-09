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
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findByUserUserIdAndProductProductID(Long userId, Long productId);
    Optional<CartItem> findByOrderOrderIDAndProductProductID(Long orderId, Long productId);


    List<CartItem> findByUser_UserId(Long userId);
    boolean existsByUser_UserIdAndProduct_ProductID(Long userId, Long productId);
    Optional<CartItem> findByUser_UserIdAndProduct_ProductID(Long userId, Long productId);
}
