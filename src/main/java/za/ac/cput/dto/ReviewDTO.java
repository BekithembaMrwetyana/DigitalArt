package za.ac.cput.dto;

import za.ac.cput.domain.Review;
import java.time.LocalDate;

public class ReviewDTO {
    private Long reviewId;
    private Long userId;
    private String userName;
    private Long productId;
    private String productName;  // This maps to Product.title
    private int rating;
    private String comment;
    private LocalDate reviewDate;

    public ReviewDTO(Review review) {
        this.reviewId = review.getReviewId();
        this.userId = review.getUser().getUserId();
        this.userName = review.getUser().getFirstName() + " " + review.getUser().getLastName();
        this.productId = review.getProduct().getProductID();
        this.productName = review.getProduct().getTitle();  // ✅ Fixed: Using getTitle()
        this.rating = review.getRating();
        this.comment = review.getComment();
        this.reviewDate = review.getReviewDate();
    }

    // Getters and Setters
    public Long getReviewId() { return reviewId; }
    public void setReviewId(Long reviewId) { this.reviewId = reviewId; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    public LocalDate getReviewDate() { return reviewDate; }
    public void setReviewDate(LocalDate reviewDate) { this.reviewDate = reviewDate; }
}