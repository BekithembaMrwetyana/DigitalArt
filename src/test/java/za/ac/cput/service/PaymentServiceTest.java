package za.ac.cput.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import za.ac.cput.domain.Payment;
import za.ac.cput.domain.Order;
import za.ac.cput.domain.enums.OrderStatus;
import za.ac.cput.domain.enums.PaymentStatus;
import za.ac.cput.factory.PaymentFactory;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
class PaymentServiceTest {

    @Autowired
    private IPaymentService service;

    private static Payment payment;

    private static Order order;

    @BeforeAll
    static void setUp(@Autowired OrderService orderService) {
        // Fetch existing order from the database
        order = orderService.getAll().stream()
                .filter(o -> OrderStatus.PENDING.equals(o.getPaymentStatus()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("An Order with status PENDING should exist in the database"));

        assertNotNull(order, "An Order should exist in the database");

        // Create Payment instances with existing Order
        payment = PaymentFactory.createPayment(LocalDate.now(), 150.0, order, PaymentStatus.PENDING);


        assertNotNull(payment);
    }
    @Test
    void create() {
        Payment newPayment = service.create(payment);
        assertNotNull(newPayment);
        System.out.println(newPayment);
    }

    @Test
    void read() {
        Payment read = service.read(payment.getPaymentID());
        assertNotNull(read);
        System.out.print(read);
    }

    @Test
    void update() {
        Payment newPayment = new Payment.Builder().copy(payment).setAmount(99).build();
        Payment updated = service.update(newPayment);
        assertNotNull(updated);
        System.out.println(updated);
    }

    @Test
    void delete() {
    }

    @Test
    void getAll() {
        System.out.print(service.getAll());
    }
}