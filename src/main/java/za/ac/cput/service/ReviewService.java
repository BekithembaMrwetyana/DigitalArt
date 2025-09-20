package za.ac.cput.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ac.cput.domain.Review;
import za.ac.cput.domain.User;
import za.ac.cput.domain.Product;
import za.ac.cput.repository.ProductRepository;
import za.ac.cput.repository.ReviewRepository;
import za.ac.cput.repository.UserRepository;

import java.util.List;
/*
ReviewService.java
ReviewService POJO class
Author: Thandolwethu P Mseleku
Date: 16/07/2025
*/
@Service
public class ReviewService implements IReviewService  {


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


    public Review create(Review review) {

        User user = review.getUser();
        if (user != null && user.getUserId() == null) {
            user = userRepository.save(user);
        }


        Product product = review.getProduct();
        if (product != null && product.getProductID() == null) {
            product = productRepository.save(product);
        }


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
        return reviewRepository.findById(reviewId).orElse(null);
    }


    public Review update(Review review) {
        return reviewRepository.save(review);
    }


    public void delete(Long reviewId) {
        reviewRepository.deleteById(reviewId);
    }


    public List<Review> getAll() {
        return reviewRepository.findAll();
    }

    public List<Review> getReviewsByProduct(Long productId) {
        return reviewRepository.findReviewByProduct_ProductID(productId);
    }

}
