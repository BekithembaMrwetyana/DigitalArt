package za.ac.cput.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ac.cput.domain.OrderItem;
import za.ac.cput.repository.OrderItemRepository;

import java.util.List;

@Service
public class OrderItemService implements IOrderItemService {


    private final OrderItemRepository repository;

    @Autowired
    public OrderItemService(OrderItemRepository repository) {
        this.repository = repository;
    }

    @Override
    public OrderItem create(OrderItem orderItem) {
        return repository.save(orderItem);
    }

    @Override
    public OrderItem read(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public OrderItem update(OrderItem orderItem) {
        return repository.save(orderItem);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<OrderItem> getAll() {
        return repository.findAll();
    }

    public List<OrderItem> getByOrderID(Long orderID) {
        return repository.findByOrderID(orderID);
    }

    public List<OrderItem> getByProductID(Long productID) {
        return repository.findByProductProductID(productID);
    }

    public List<OrderItem> filterByQuantityGreaterThan(int quantity) {
        return repository.findByQuantityGreaterThan(quantity);
    }

    public List<OrderItem> filterBySubTotalBetween(double minAmount, double maxAmount) {
        return repository.findBySubTotalBetween(minAmount, maxAmount);
    }
}

