package za.ac.cput.repository;

import za.ac.cput.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.ac.cput.domain.enums.PaymentStatus;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findByPaymentDate(LocalDate paymentDate);
    List<Payment> findByAmount(double amount);
    List<Payment> findByPaymentStatus(PaymentStatus status);
    //List<Payment> findByPaymentMethod(PaymentMethod method)
    //List<Payment> findByOrderUserId(Long userId);

}
