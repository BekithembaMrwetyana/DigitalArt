package factory;
/*
PaymentFactory.java
Payment Factory class
Author: Bekithemba Mrwetyana 222706066
Date: 17 May 2025
*/
import domain.Payment;
import util.Helper;

import java.time.LocalDate;

public class PaymentFactory {

    public static Payment createPayment(String paymentID, LocalDate paymentDate, double amount, String status, String method){

        if(Helper.isValidPaymentID(paymentID)){
            return null;
        }
        if(Helper.isValidPaymentDate(paymentDate)){
            return null;
        }
        if(Helper.isValidAmount(amount)){
            return null;
        }
        if(Helper.isValidStatus(status)){
            return null;
        }
        if(Helper.isValidMethod(method)){
            return null;
        }

        
        return new Payment.Builder()
                .setPaymentID(paymentID)
                .setAmount(amount)
                .setStatus(status)
                .setMethod(method)
                .build();
    }
    public static Payment createPayment(LocalDate paymentDate, double amount, String status, String method){

        if(Helper.isValidAmount(amount)){
            return null;
        }
        if(Helper.isValidStatus(status)){
            return null;
        }
        if(Helper.isValidMethod(method)){
            return null;
        }

        LocalDate now = LocalDate.now();
        return new Payment.Builder()
                .setPaymentDate(now)
                .setAmount(amount)
                .setStatus(status)
                .setMethod(method)
                .build();
    }
}
