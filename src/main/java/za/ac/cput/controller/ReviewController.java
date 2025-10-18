package za.ac.cput.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.domain.Review;
import za.ac.cput.dto.ReviewDTO;
import za.ac.cput.service.ReviewService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/review")
public class ReviewController {

    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }


    @PostMapping("/create/{userId}")
    public ResponseEntity<ReviewDTO> create(@PathVariable Long userId, @RequestBody Review review) {
        try {
            Review createdReview = reviewService.create(userId, review);
            ReviewDTO reviewDTO = new ReviewDTO(createdReview);
            return new ResponseEntity<>(reviewDTO, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/read/{reviewId}")
    public ResponseEntity<ReviewDTO> read(@PathVariable Long reviewId) {
        try {
            Review review = reviewService.read(reviewId);
            ReviewDTO reviewDTO = new ReviewDTO(review);
            return new ResponseEntity<>(reviewDTO, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }


    @PutMapping("/update")
    public ResponseEntity<ReviewDTO> update(@RequestBody Review review) {
        try {
            Review updatedReview = reviewService.update(review);
            ReviewDTO reviewDTO = new ReviewDTO(updatedReview);
            return new ResponseEntity<>(reviewDTO, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }


    @DeleteMapping("/delete/{reviewId}")
    public ResponseEntity<Void> delete(@PathVariable Long reviewId) {
        try {
            reviewService.delete(reviewId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("/getAll")
    public ResponseEntity<List<ReviewDTO>> getAll() {
        try {
            List<Review> reviews = reviewService.getAll();
            List<ReviewDTO> reviewDTOs = reviews.stream()
                    .map(ReviewDTO::new)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(reviewDTOs, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/product/{productId}")
    public ResponseEntity<List<ReviewDTO>> getReviewsByProduct(@PathVariable Long productId) {
        try {
            List<Review> reviews = reviewService.getReviewsByProduct(productId);
            List<ReviewDTO> reviewDTOs = reviews.stream()
                    .map(ReviewDTO::new)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(reviewDTOs, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ReviewDTO>> getReviewsByUser(@PathVariable Long userId) {
        try {
            List<Review> reviews = reviewService.getReviewsByUser(userId);
            List<ReviewDTO> reviewDTOs = reviews.stream()
                    .map(ReviewDTO::new)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(reviewDTOs, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}