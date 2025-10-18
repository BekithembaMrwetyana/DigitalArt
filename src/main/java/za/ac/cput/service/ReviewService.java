package za.ac.cput.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ac.cput.domain.Product;
import za.ac.cput.domain.Review;
import za.ac.cput.domain.User;
import za.ac.cput.repository.ProductRepository;
import za.ac.cput.repository.ReviewRepository;
import za.ac.cput.repository.UserRepository;

import java.util.List;

/**
 ReviewService.java
 Service class for Review operations
 Author: Thandolwethu P Mseleku
 Date: 16/07/2025
 */
@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository,
                         UserRepository userRepository,
                         ProductRepository productRepository) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }


    public Review create(Long userId, Review review) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));


        Product product = productRepository.findById(review.getProduct().getProductID())
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + review.getProduct().getProductID()));


        Review savedReview = new Review.Builder()
                .setUser(user)
                .setProduct(product)
                .setRating(review.getRating())
                .setComment(review.getComment())
                .setReviewDate(review.getReviewDate())
                .build();

        return reviewRepository.save(savedReview);
    }


    public Review read(Long reviewId) {
        return reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found with ID: " + reviewId));
    }


    public Review update(Review review) {
        if (!reviewRepository.existsById(review.getReviewId())) {
            throw new RuntimeException("Review not found with ID: " + review.getReviewId());
        }
        return reviewRepository.save(review);
    }

    public void delete(Long reviewId) {
        if (!reviewRepository.existsById(reviewId)) {
            throw new RuntimeException("Review not found with ID: " + reviewId);
        }
        reviewRepository.deleteById(reviewId);
    }


    public List<Review> getAll() {
        return reviewRepository.findAll();
    }


    public List<Review> getReviewsByProduct(Long productId) {
        return reviewRepository.findReviewByProduct_ProductID(productId);
    }


    public List<Review> getReviewsByUser(Long userId) {
        return reviewRepository.findReviewByUser_UserId(userId);
    }


    public List<Review> getReviewsByRating(int rating) {
        return reviewRepository.findByRating(rating);
    }


    public List<Review> getReviewsByProduct(Product product) {
        return reviewRepository.findByProduct(product);
    }


    public List<Review> getReviewsByProductAndRating(Product product, int rating) {
        return reviewRepository.findByProductAndRating(product, rating);
    }
}