package za.ac.cput.service;
/*
IPaymentService.java
Payment service interface
Author: Bekithemba Mrwetyana (222706066)
Date: 5 July 2025
*/
import za.ac.cput.domain.Payment;

import java.util.List;


public interface IPaymentService extends IService <Payment, Long>{

    List<Payment> getAll();

}
