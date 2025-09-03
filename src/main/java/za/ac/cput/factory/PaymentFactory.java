package za.ac.cput.factory;
/*
PaymentFactory.java
Payment Factory class
Author: Bekithemba Mrwetyana 222706066
Date: 17 May 2025
*/
import za.ac.cput.domain.Order;
import za.ac.cput.domain.Payment;
import za.ac.cput.domain.enums.PaymentStatus;
import za.ac.cput.util.Helper;

import java.time.LocalDate;

public class PaymentFactory {

    public static Payment createPayment(LocalDate paymentDate, double amount, Order order, PaymentStatus status){

        if (amount <= 0) {
            throw new IllegalArgumentException("Payment amount must be positive");
        }
        
        return new Payment.Builder()
                .setAmount(amount)
                .build();
    }

}
