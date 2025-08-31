package za.ac.cput.service;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.ac.cput.domain.*;
import za.ac.cput.factory.ReviewFactory;
import za.ac.cput.repository.ProductRepository;
import za.ac.cput.repository.UserRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ReviewServiceTest {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryService categoryService;

    private User savedUser;
    private Product savedProduct;
    private Review savedReview;

    @BeforeAll
    void setup() {

        savedUser = userRepository.save(new User.Builder()
                .setFirstName("Thando")
                .setLastName("Mseleku")
                .setPassword("password123")
                .setCreateDate(LocalDate.now())
                .setLastLogin(LocalDateTime.now())
                .build());

        assertNotNull(savedUser, "User should be saved");


        Category category = categoryService.create(new Category.Builder()
                .setName("Paintings")
                .setDescription("Digital Painting Category")
                .build());

        assertNotNull(category, "Category should be saved");

        savedProduct = productRepository.save(new Product.Builder()
                .setTitle("Sunset Art")
                .setDescription("Beautiful digital sunset painting")
                .setPrice(59.99)
                .setCategory(category)
                .build());

        assertNotNull(savedProduct, "Product should be saved");
    }

    @Test
    @Order(1)
    void testCreateReview() {
        savedReview = reviewService.create(ReviewFactory.createReview(
                5,
                "Amazing quality artwork!",
                LocalDate.now(),
                savedUser,
                savedProduct
        ));

        assertNotNull(savedReview, "Review should be saved");
        assertNotNull(savedReview.getReviewId(), "Review ID should be generated");
        assertEquals(5, savedReview.getRating(), "Rating should match");
        assertEquals("Amazing quality artwork!", savedReview.getComment(), "Comment should match");
    }

    @Test
    @Order(2)
    void testReadReview() {
        Review read = reviewService.read(savedReview.getReviewId());
        assertNotNull(read, "Read review should not be null");
        assertEquals("Amazing quality artwork!", read.getComment(), "Comment should match");
    }

    @Test
    @Order(3)
    void testUpdateReview() {
        Review updated = new Review.Builder()
                .copy(savedReview)
                .setRating(4)
                .setComment("Great art, but shipping was slow")
                .build();

        savedReview = reviewService.update(updated);

        assertNotNull(savedReview, "Updated review should not be null");
        assertEquals(4, savedReview.getRating(), "Rating should be updated to 4");
        assertEquals("Great art, but shipping was slow", savedReview.getComment(), "Comment should be updated");
    }

    @Test
    @Order(4)
    void testDeleteReview() {
        reviewService.delete(savedReview.getReviewId());
        assertNull(reviewService.read(savedReview.getReviewId()), "Deleted review should be null");
    }

    @Test
    @Order(5)
    @Transactional
    void e_getAll() {
        System.out.println(reviewService.getAll());
    }

}


