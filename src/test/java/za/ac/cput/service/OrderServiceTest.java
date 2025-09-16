package za.ac.cput.service;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.ac.cput.domain.Order;
import za.ac.cput.domain.User;
import za.ac.cput.domain.enums.OrderStatus;
import za.ac.cput.repository.OrderRepository;
import za.ac.cput.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
public class OrderServiceTest {


    @Autowired
    private OrderService service;

    @Autowired
    private OrderRepository repository;

    @Autowired
    private UserRepository userRepository;

    private Order testOrder;
    private User dummyUser;

    @BeforeEach
    void setUp() {
        repository.deleteAll();
        userRepository.deleteAll();


        dummyUser = new User.Builder()
                .setFirstName("Test")
                .setLastName("User")
                .setPassword("password")
                .setRole(za.ac.cput.domain.enums.Role.CUSTOMER)
                .build();
        dummyUser = userRepository.save(dummyUser);


        testOrder = new Order.Builder()
                .setCartItem(Collections.emptyList())
                .setTotalAmount(200.00)
                .setOrderAmount(150.00)
                .setOrderDate(LocalDateTime.now())
                //.setPaymentStatus(OrderStatus.PENDING)
                .build();
        testOrder.setUser(dummyUser);

        service.create(testOrder);
    }

    @Test
    void a_testCreate() {
        assertNotNull(testOrder.getOrderID());
        System.out.println("Created order: " + testOrder);
    }

    @Test
    @Transactional
    void b_testRead() {
        Order found = service.read(testOrder.getOrderID());
        assertNotNull(found);
        assertEquals(testOrder.getOrderID(), found.getOrderID());
        System.out.println("Read order: " + found);
    }

    @Test
    void c_testUpdate() {
        Order updated = new Order.Builder()
                .copy(testOrder)
                //.setPaymentStatus(OrderStatus.COMPLETED)
                .build();
        updated.setUser(dummyUser); // keep the user for JPA

        Order result = service.update(updated);
        assertEquals(OrderStatus.COMPLETED, result.getPaymentStatus());
        System.out.println("Updated order: " + result);
    }

    @Test
    @Transactional
    void d_testGetAll() {
        List<Order> orders = service.getAll();
        assertNotNull(orders);
        assertFalse(orders.isEmpty());
        System.out.println("All orders: " + orders);
    }
}
