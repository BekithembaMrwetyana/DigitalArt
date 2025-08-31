package za.ac.cput.repository;

import za.ac.cput.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.ac.cput.domain.enums.OrderStatus;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {


    List<Order> findByUserUserId(Long userId);

    List<Order> findByPaymentStatus(OrderStatus paymentStatus);

    List<Order> findByTotalAmountGreaterThan(double amount);
}
