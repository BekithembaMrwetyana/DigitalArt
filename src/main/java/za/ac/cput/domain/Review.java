package za.ac.cput.domain;

import jakarta.persistence.*;

import java.time.LocalDate;
/*
Review.java
Review POJO class
Author: Thandolwethu P Mseleku
Date: 16/07/2025
*/
@Entity
@Table( name = "review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name ="productID", nullable = false)
    private Product product;

    private int rating;
    private String comment;
    private LocalDate reviewDate;

    protected Review() {
    }

    public Review(Builder builder){
        this.reviewId = builder.reviewId;
        this.user = builder.user;
        this.product = builder.product;
        this.rating = builder.rating;
        this.comment = builder.comment;
        this.reviewDate = builder.reviewDate;

    }
    public Long getReviewId() {
        return reviewId;
    }

    public User getUser() {
        return user;
    }

    public Product getProduct() {
        return product;
    }

    public int getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

    public LocalDate getReviewDate() {
        return reviewDate;
    }

    @Override
    public String toString() {
        return "Review{" +
                "reviewId=" + reviewId +
                ", user=" + user +
                ", product=" + product +
                ", rating=" + rating +
                ", comment='" + comment + '\'' +
                ", reviewDate=" + reviewDate +
                '}';
    }

    public static class Builder{
        private Long reviewId;
        private User user;
        private Product product;
        private int rating;
        private String comment;
        private LocalDate reviewDate;

        public Builder setReviewId(Long reviewId) {
            this.reviewId = reviewId;
            return this;
        }

        public Builder setUser(User user) {
            this.user = user;
            return this;
        }

        public Builder setProduct(Product product) {
            this.product = product;
            return this;
        }

        public Builder setRating(int rating) {
            this.rating = rating;
            return this;
        }

        public Builder setComment(String comment) {
            this.comment = comment;
            return this;
        }

        public Builder setReviewDate(LocalDate reviewDate) {
            this.reviewDate = reviewDate;
            return this;
        }
        public Builder copy(Review review) {
            this.reviewId=review.reviewId;
            this.user=review.user;
            this.product=review.product;
            this.rating=review.rating;
            this.comment=review.comment;
            this.reviewDate=review.reviewDate;
            return this;
        }
        public Review build(){
            return new Review(this);
        }
    }

}
