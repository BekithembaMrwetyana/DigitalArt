package repository;

import domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

    List<Product> findByCategoryID(String categoryID);

    List<Product> findByPriceBetween(double minPrice, double maxPrice);

    List<Product> findByTitleContainingIgnoreCase(String keyword);

    List<Product> findByPriceLessThan(double maxPrice);
}