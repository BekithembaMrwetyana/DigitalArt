package za.ac.cput.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.ac.cput.domain.Product;
import za.ac.cput.domain.User;
import za.ac.cput.domain.Wishlist;

import java.util.List;


@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Long> {

    List<Wishlist> findByUser(User user);

    List<Wishlist> findByUserAndProduct(User user, Product product);

    void deleteByUserAndProduct(User user, Product product);
}

