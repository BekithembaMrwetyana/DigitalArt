package repository;

import domain.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {

    List<OrderItem> findByOrderUserID(int userID);

    List<OrderItem> findByOrderID(int orderID);

    List<OrderItem> findByProductProductID(String productID);

    List<OrderItem> findByQuantityGreaterThan(int quantity);

    List<OrderItem> findBySubTotalBetween(double minAmount, double maxAmount);
}
