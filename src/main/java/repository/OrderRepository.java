package repository;

import domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {



    List<Order> findByUserID(int userID);

    List<Order> findByPaymentStatus(String paymentStatus);

    List<Order> findByTotalAmountGreaterThan(double amount);
}
