package za.ac.cput.service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import za.ac.cput.domain.Review;
import za.ac.cput.domain.User;
import za.ac.cput.domain.Product;
import za.ac.cput.factory.ReviewFactory;

import java.time.LocalDate;
import java.util.List;

@Component
public class ReviewDataInitializer implements CommandLineRunner {

    private final ReviewService reviewService;
    private final UserService userService;
    private final ProductService productService;

    public ReviewDataInitializer(ReviewService reviewService,
                                 UserService userService,
                                 ProductService productService) {
        this.reviewService = reviewService;
        this.userService = userService;
        this.productService = productService;
    }

    @Override
    public void run(String... args) throws Exception {


        User user9  = userService.read(9L);
        User user14 = userService.read(14L);

        Product product14 = productService.read(14L);
        Product product15 = productService.read(15L);


        List<Review> existingReviews = reviewService.getAll();


        existingReviews.stream()
                .filter(r -> r.getReviewId() == 1L)
                .findFirst()
                .ifPresent(r -> {
                    Review updated = new Review.Builder()
                            .copy(r)
                            .setComment("There is something missing from this artwork, not impressed at all!")
                            .setRating(2)
                            .setReviewDate(LocalDate.of(2025, 10, 19))
                            .build();
                    reviewService.update(updated);
                });


        existingReviews.stream()
                .filter(r -> r.getReviewId() == 2L)
                .findFirst()
                .ifPresent(r -> {
                    Review updated = new Review.Builder()
                            .copy(r)
                            .setComment("Good work by artist, it's just refreshing")
                            .setRating(4)
                            .setReviewDate(LocalDate.of(2025, 10, 20))
                            .build();
                    reviewService.update(updated);
                });


        existingReviews.stream()
                .filter(r -> r.getReviewId() == 3L)
                .findFirst()
                .ifPresent(r -> {
                    Review updated = new Review.Builder()
                            .copy(r)
                            .setComment("Such an amazing painting, will definitely buy again")
                            .setRating(5)
                            .setReviewDate(LocalDate.of(2025, 10, 21))
                            .build();
                    reviewService.update(updated);
                });


        existingReviews.stream()
                .filter(r -> r.getReviewId() == 4L)
                .findFirst()
                .ifPresent(r -> {
                    Review updated = new Review.Builder()
                            .copy(r)
                            .setComment("Beautiful colors and composition, very satisfied with this purchase!")
                            .setRating(5)
                            .setReviewDate(LocalDate.of(2025, 10, 22))
                            .build();
                    reviewService.update(updated);
                });

        System.out.println("ReviewDataInitializer: Existing reviews updated, including the fourth review!");
    }
}
