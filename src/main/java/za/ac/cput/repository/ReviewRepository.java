package za.ac.cput.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.ac.cput.domain.Product;
import za.ac.cput.domain.Review;

import java.util.List;

/*
ReviewRepository.java
ReviewRepository interface
Author: Thandolwethu P Mseleku
Date: 16/07/2025
*/
@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {


    List<Review> findByProduct(Product product);


    List<Review> findByRating(int rating);


    List<Review> findByProductAndRating(Product product, int rating);


    List<Review> findReviewByProduct_ProductID(Long productId);


    List<Review> findReviewByUser_UserId(Long userId);
}