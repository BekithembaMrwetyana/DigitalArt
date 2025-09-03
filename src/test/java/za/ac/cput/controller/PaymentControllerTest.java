package za.ac.cput.controller;

import za.ac.cput.domain.Cart;
import za.ac.cput.domain.Order;
import za.ac.cput.domain.Payment;
import za.ac.cput.domain.enums.OrderStatus;
import za.ac.cput.domain.enums.PaymentStatus;
import za.ac.cput.factory.PaymentFactory;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.MethodName.class)
class PaymentControllerTest {

    private static Payment payment;

    @Autowired
    private TestRestTemplate restTemplate;

    private static final String BASE_URL = "https://localhost:8080/ADP_Capstone_Project/payment";

    public static void setup(){
        Order order = new Order.Builder()
                .setOrderID(1L)
                .setOrderDate(LocalDateTime.now())
                .setTotalAmount(400)
                //.setPaymentStatus(OrderStatus.PENDING)
                //.setUser(user) // assuming you have a User object
                .build();
        payment = PaymentFactory.createPayment(LocalDate.of(2025, 8, 2), 400, order, PaymentStatus.PENDING);
    }

    @Test
    void create() {
        String url = BASE_URL + "/read/" + payment.getPaymentID();
        ResponseEntity<Payment> response = this.restTemplate.getForEntity(url, Payment.class);
        assertEquals(payment.getPaymentID(), response.getBody().getPaymentID());
        System.out.print("Read: " + response.getBody());
    }

    @Test
    void read() {
        String url = BASE_URL + "/read/" + payment.getPaymentID();
        ResponseEntity<Payment> response = this.restTemplate.getForEntity(url, Payment.class);
        assertEquals(payment.getPaymentID(), response.getBody().getPaymentID());
        System.out.print("Read: " + response.getBody());
    }

    @Test
    void update() {
        Payment updatedPayment = new Payment.Builder().copy(payment).setPaymentID(123L).setAmount(400).build();
        String url = BASE_URL + "/update";
        this.restTemplate.put(url, updatedPayment);

        ResponseEntity<Payment> response = this.restTemplate.getForEntity(BASE_URL + "/read/" + updatedPayment.getPaymentID(), Payment.class);

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertNotNull(response.getBody());
        //assertEquals(updatedCart.getCartID(), response.getBody().getCartID());
        System.out.println("Updated: " + response.getBody());
    }

    @Test
    void delete() {
        String url = BASE_URL + "/delete/" + payment.getPaymentID();
        this.restTemplate.delete(url);

        ResponseEntity<Cart> response = this.restTemplate.getForEntity(BASE_URL + "/read/" + payment.getPaymentID(), Cart.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        System.out.println("Deleted: " + payment.getPaymentID());
    }

    @Test
    void getAll() {
        String url = BASE_URL + "/getAll";
        ResponseEntity<Payment> response = this.restTemplate.getForEntity(url, Payment.class);
        assertNotNull(response.getBody());
        //assertTrue(response.getBody().length > 0);
        System.out.println("Get All: " + response.getBody());
        //for (Payment payment : response.getBody()){
        //    System.out.println(payment);
        //}
    }
}