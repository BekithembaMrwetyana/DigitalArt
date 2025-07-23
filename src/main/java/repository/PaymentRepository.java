package repository;

import domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, String> {

    List<Payment> findByStatus(String status);

    List<Payment> findByPaymentDate(LocalDate paymentDate);
    List<Payment> findByMethod(String method);
    List<Payment> findByAmount(double amount);
    List<Payment> findByPaymentID(String paymentID);

}
