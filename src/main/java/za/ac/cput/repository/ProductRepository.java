package za.ac.cput.repository;

import za.ac.cput.domain.Category;
import za.ac.cput.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAllByCategory_CategoryId(Long categoryId);

    List<Product> findAllByCategoryIsNull();

    List<Product> findByPriceBetween(double minPrice, double maxPrice);

    List<Product> findByTitleContainingIgnoreCase(String keyword);

    List<Product> findByPriceLessThanEqual(double maxPrice);
}