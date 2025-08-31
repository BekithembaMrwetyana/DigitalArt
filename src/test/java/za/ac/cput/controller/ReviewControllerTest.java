package za.ac.cput.controller;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import za.ac.cput.domain.*;
import za.ac.cput.factory.ReviewFactory;
import za.ac.cput.service.*;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ReviewControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ReviewService reviewService;

    private final String baseUrl = "/review";

    private User savedUser;
    private Category savedCategory;
    private Product savedProduct;
    private Review savedReview;

    @BeforeAll
    void init() {

        savedUser = userService.create(
                new User.Builder()
                        .setFirstName("Thando")
                        .setLastName("Mseleku")
                        .setPassword("password123")
                        .build()
        );


        savedCategory = categoryService.create(
                new Category.Builder()
                        .setName("Paintings")
                        .setDescription("Digital Painting Category")
                        .build()
        );


        savedProduct = productService.create(
                new Product.Builder()
                        .setTitle("Sunset Art")
                        .setDescription("Beautiful digital sunset painting")
                        .setPrice(30.000)
                        .setCategory(savedCategory)
                        .build()
        );
    }

    @Test
    @Order(1)
    void a_createReview() {
        Review review = ReviewFactory.createReview(
                5,
                "Amazing artwork!",
                LocalDate.now(),
                savedUser,
                savedProduct
        );

        ResponseEntity<Review> response = restTemplate.postForEntity(
                baseUrl + "/create",
                review,
                Review.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getReviewId());

        savedReview = response.getBody();
        System.out.println("Created Review: " + savedReview);
    }

    @Test
    @Order(2)
    void b_readReview() {
        Review review = restTemplate.getForObject(
                baseUrl + "/read/" + savedReview.getReviewId(),
                Review.class
        );
        assertNotNull(review);
        System.out.printf("Review: %s\n", review);
    }

    @Test
    @Order(3)
    void c_updateReview() {
        Review updatedReview = new Review.Builder()
                .copy(savedReview)
                .setRating(4)
                .setComment("Great art, But website took long to load")
                .build();

        ResponseEntity<Review> response = restTemplate.postForEntity(
                baseUrl + "/update",
                updatedReview,
                Review.class
        );

        assertNotNull(response.getBody());
        savedReview = response.getBody();
        System.out.printf("Updated Review: %s\n", savedReview);
    }

    @Test
    @Order(4)
    void d_getAllReviews() {
        List<Review> allReviews = reviewService.getAll();
        assertFalse(allReviews.isEmpty());
        System.out.printf("All Reviews: %s\n", allReviews);
    }

    @Test
    @Order(5)
    void e_deleteReview() {
        restTemplate.delete(baseUrl + "/delete/" + savedReview.getReviewId());
        System.out.println("Deleted Review with ID: " + savedReview.getReviewId());
    }
}
