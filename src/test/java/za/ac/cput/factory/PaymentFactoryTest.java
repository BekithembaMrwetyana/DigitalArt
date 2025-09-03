package za.ac.cput.factory;

/*
PaymentFactoryTest.java
Unit test for PaymentFactory
Author: Bekithemba Mrwetyana (222706066)
Date: 16 May 2025
*/

import za.ac.cput.domain.Payment;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import za.ac.cput.domain.enums.PaymentStatus;
import za.ac.cput.factory.PaymentFactory;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PaymentFactoryTest {

//    @Test
//    void createPayment_success() {
//        Payment payment = PaymentFactory.createPayment(LocalDate.now(), 100.0, PaymentStatus.COMPLETED);
//        assertNotNull(payment);
//        System.out.print(payment);
//    }
}
