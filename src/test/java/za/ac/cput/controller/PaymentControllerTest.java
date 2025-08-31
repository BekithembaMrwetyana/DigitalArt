package za.ac.cput.controller;

import org.junit.jupiter.api.*;
import za.ac.cput.domain.Cart;
import za.ac.cput.domain.Payment;
import za.ac.cput.domain.enums.PaymentStatus;
import za.ac.cput.factory.PaymentFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.MethodName.class)
class PaymentControllerTest {

    private static Payment payment;

    @Autowired
    private TestRestTemplate restTemplate;

    private static final String BASE_URL = "http://localhost:8080/ADP_Capstone_Project/payment";

    @BeforeAll
    public static void setup(){
        payment = PaymentFactory.createPayment(LocalDate.of(2025, 8, 2), 400, PaymentStatus.PENDING);
    }

    @Test
    @Order(1)
    void create() {
        String url = BASE_URL + "/create";
        ResponseEntity<Payment> postResponse = this.restTemplate.postForEntity(url, payment, Payment.class);
        assertNotNull(postResponse);
        Payment paymentSaved = postResponse.getBody();
        assertEquals(payment.getPaymentID(), paymentSaved.getPaymentID());
        System.out.print("Read: " + paymentSaved);
    }

    @Test
    @Order(2)
    void read() {
        String url = BASE_URL + "/read/" + payment.getPaymentID();
        ResponseEntity<Payment> response = this.restTemplate.getForEntity(url, Payment.class);
        assertEquals(payment.getPaymentID(), response.getBody().getPaymentID());
        System.out.print("Read: " + response.getBody());
    }

    @Test
    @Order(3)
    void update() {
        Payment updatedPayment = new Payment.Builder().copy(payment)
                .setAmount(400)
                .build();
        String url = BASE_URL + "/update";
        this.restTemplate.put(url, updatedPayment);

        ResponseEntity<Payment> response = this.restTemplate.getForEntity(BASE_URL + "/read/" + updatedPayment.getPaymentID(), Payment.class);

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertNotNull(response.getBody());
        //assertEquals(updatedCart.getCartID(), response.getBody().getCartID());
        System.out.println("Updated: " + response.getBody());
    }

    @Test
    @Order(4)
    void delete() {
        String url = BASE_URL + "/delete/" + payment.getPaymentID();
        this.restTemplate.delete(url);

        ResponseEntity<Payment> response = this.restTemplate.getForEntity(BASE_URL + "/read/" + payment.getPaymentID(), Payment.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        //assertNotNull(response.getBody());
        System.out.println("Deleted: " + "true");
    }

    @Test
    @Order(5)
    void getAll() {
        String url = BASE_URL + "/getAll";
        ResponseEntity<Payment[]> response = this.restTemplate.getForEntity(url, Payment[].class);
        assertNotNull(response.getBody());
        //assertTrue(response.getBody().length > 0);
        System.out.println("Get All: ");
        for (Payment payment : response.getBody()){
            System.out.println(payment);
        }
    }
}