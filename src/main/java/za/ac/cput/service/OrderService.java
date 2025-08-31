package za.ac.cput.service;

/*
OrderService.java
Order service implementation
Author: Mpilonhle Zimela Mzimela 230197833
Date: 27 May 2025
*/

import za.ac.cput.domain.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ac.cput.repository.OrderRepository;

import java.util.List;

@Service
public class OrderService implements IOrderService {

    private final OrderRepository repository;

    @Autowired
    public OrderService(OrderRepository repository) {
        this.repository = repository;
    }

    @Override
    public Order create(Order order) {
        return repository.save(order);
    }

    @Override
    public Order read(Long orderID) {
        return repository.findById(orderID).orElse(null);
    }

    @Override
    public Order update(Order order) {
        return repository.save(order);
    }

    @Override
    public void delete(Long orderID) {
        repository.deleteById(orderID);
    }

    @Override
    public List<Order> getAllOrders() {
        return List.of();
    }

    @Override
    public List<Order> getAll() {
        return repository.findAll();
    }
    public List<Order> getOrdersByUserId(Long userId) {
        return repository.findByUserUserId(userId);
    }
}
