package za.ac.cput.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import za.ac.cput.domain.Discount;
import za.ac.cput.factory.DiscountFactory;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.MethodName.class)
class DiscountControllerTest {

    private static Discount discount;

    @Autowired
    private TestRestTemplate restTemplate;

    private static final String BASE_URL = "/discount";

    @BeforeEach
    void setup() {
        discount = DiscountFactory.createDiscount(
                "SUMMER25",
                new BigDecimal("25"),
                LocalDate.of(2025, 8, 1),
                LocalDate.of(2025, 8, 31)
        );
    }


    @Test
    @Order(1)
    void create() {
        String url = BASE_URL + "/create";
        ResponseEntity<Discount> postResponse = this.restTemplate.postForEntity(url, discount, Discount.class);
        assertNotNull(postResponse);
        //assertEquals(discount.getDiscountId(), createdDiscount.getDiscountId());
        Discount discountSaved = postResponse.getBody();
        assertEquals(discount.getDiscountId(), discountSaved.getDiscountId());
        System.out.println("Created: " + discountSaved);
    }

    @Test
    @Order(2)
    void read() {
        String url = BASE_URL + "/read/" + discount.getDiscountId();
        ResponseEntity<Discount> response = this.restTemplate.getForEntity(url, Discount.class);
        assertEquals(discount.getDiscountId(), response.getBody().getDiscountId());
        System.out.print("Read: " + response.getBody());
    }

    @Test
    @Order(3)
    void update() {
        Discount updatedDiscount = new Discount.Builder().copy(discount).build(); // keep same ID
        String url = BASE_URL + "/update";
        this.restTemplate.put(url, updatedDiscount);

        ResponseEntity<Discount> response =
                this.restTemplate.getForEntity(BASE_URL + "/read/" + updatedDiscount.getDiscountId(), Discount.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        System.out.println("Updated: " + response.getBody());
    }

    @Test
    @Order(4)
    void delete() {
        String url = BASE_URL + "/delete/" + discount.getDiscountId();
        this.restTemplate.delete(url);

        ResponseEntity<Discount> response =
                this.restTemplate.getForEntity(BASE_URL + "/read/" + discount.getDiscountId(), Discount.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode()); // requires controller fix
        System.out.println("Deleted: " + discount.getDiscountId());
    }


    @Test
    @Order(5)
    void getAll() {
        String url = BASE_URL + "/getAll";
        ResponseEntity<Discount[]> response = this.restTemplate.getForEntity(url, Discount[].class);
        assertNotNull(response.getBody());
        //assertTrue(response.getBody().length > 0);
        System.out.println("Get All: " + response.getBody());
        for (Discount discount : response.getBody()){
            System.out.println(discount);
        }
    }
}