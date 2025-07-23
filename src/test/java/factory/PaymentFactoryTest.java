package factory;

/*
PaymentFactoryTest.java
Unit test for PaymentFactory
Author: Bekithemba Mrwetyana (222706066)
Date: 16 May 2025
*/

import domain.Payment;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PaymentFactoryTest {

    @Test
    void createPayment_success() {
        Payment payment = PaymentFactory.createPayment("P001", LocalDate.now(), 100.0, "Completed", "Credit Card");
        assertNotNull(payment);
        System.out.print(payment);
    }

    @Test
    void createPayment_invalidAmount() {
        Payment payment = PaymentFactory.createPayment("P002", LocalDate.now(), -50.0, "Pending", "Debit Card");
        assertNull(payment);
    }

    @Test
    void createPayment_invalidStatus() {
        Payment payment = PaymentFactory.createPayment("P003", LocalDate.now(), 200.0, "", "PayPal");
        assertNull(payment);
    }

    @Test
    void createPayment_invalidMethod() {
        Payment payment = PaymentFactory.createPayment("P004", LocalDate.now(), 150.0, "Failed", "");
        assertNull(payment);
    }
    @Test
    void createPayment_nullValues() {
        Payment payment = PaymentFactory.createPayment(null, LocalDate.now(), 0.0, null, null);
        assertNull(payment);
    }
    @Test
    void createPayment_emptyValues() {
        Payment payment = PaymentFactory.createPayment("", LocalDate.now(), 0.0, "", "");
        assertNull(payment);
    }
    @Test
    void createPayment_invalidDate() {
        Payment payment = PaymentFactory.createPayment("P005", LocalDate.of(2025, 5, 16), 300.0, "Completed", "Bank Transfer");
        assertNotNull(payment);
        System.out.print(payment);
    }


}
