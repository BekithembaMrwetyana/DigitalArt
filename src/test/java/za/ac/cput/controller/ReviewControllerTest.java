package za.ac.cput.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import za.ac.cput.domain.Product;
import za.ac.cput.domain.Review;
import za.ac.cput.domain.User;
import za.ac.cput.factory.ReviewFactory;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ReviewControllerTest {

private static Review review;
private static User user;
private static Product product;

@Autowired
private TestRestTemplate restTemplate;
    private static final String BASE_URL = "http://localhost:8080/ADP_Capstone_Project/review";

@BeforeAll
    public static void setUp() {

      user = new User.Builder()
            .setFirstName("Thando")
            .setLastName("Mseleku")
            //.setEmail("123@gmail.com")
            .setPassword("password123")
            .build();

      product = new Product.Builder()
            .setProductID(1L)
            .setTitle("Digital Art")
            .setDescription("A beautiful digital painting")
            .setPrice(49.99)
            //.setCategoryID("C001")
            .build();

    review = ReviewFactory.createReview(
            5, "Exceptional painting", LocalDate.now(), user, product
    );

}


    @Test
    @Order(1)
    void a_create() {
        String url = BASE_URL + "/create";
        ResponseEntity<Review> postResponse = restTemplate.postForEntity(url, review, Review.class);
        assertNotNull(postResponse);
        Review reviewSaved = postResponse.getBody();
        assertEquals(review.getReviewId(), reviewSaved.getReviewId());
        System.out.println("createdReview = " + reviewSaved);
    }

    @Test
    @Order(2)
    void read() {
    String url = BASE_URL + "/read/" + review.getReviewId();
    ResponseEntity<Review> response = this.restTemplate.getForEntity(url, Review.class);
    assertEquals(review.getReviewId(), response.getBody().getReviewId());
        System.out.println("read = " + response.getBody());
    }

    @Test
    @Order(3)
    void update() {
    Review updatedReview= new Review.Builder().copy(review)
            .setRating(4)
            .build();
    String url = BASE_URL + "/update";
    this.restTemplate.put(url, updatedReview);
    ResponseEntity<Review> response = this.restTemplate.getForEntity(BASE_URL + "/read/"+ updatedReview.getReviewId(), Review.class);
    assertEquals(response.getStatusCode(), HttpStatus.OK);
    assertNotNull(response.getBody());
    //assertEquals(updatedReview.getRating(), response.getBody().getRating());
    System.out.println("updated = " + response.getBody());

    }

    @Test
    @Order(4)
    void delete() {
        String url = BASE_URL + "/delete/" + review.getReviewId();
        this.restTemplate.delete(url);
        ResponseEntity<Review> response = this.restTemplate.getForEntity(BASE_URL + "/read/" + review.getReviewId(), Review.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        System.out.println("deleted:" + "true");
    }

    @Test
    void getAll() {
        String url = BASE_URL + "/getAll";
        ResponseEntity<Review[]> response = this.restTemplate.getForEntity(url, Review[].class);
        assertNotNull(response.getBody());
        //assertTrue(response.getBody().length > 0);
        System.out.println("Get All = " + response.getBody());
    }
}