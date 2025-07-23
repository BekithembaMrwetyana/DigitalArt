package repository;

import domain.Cart;
import domain.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, String> {

    List<Cart> findByUserID(String userID);
    List<Cart> findByCartID(String cartID);
    List<Cart> findByCartItem(CartItem cartItem);

}
