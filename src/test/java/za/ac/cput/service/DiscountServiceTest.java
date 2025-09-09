package za.ac.cput.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.ac.cput.domain.Discount;
import za.ac.cput.factory.DiscountFactory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
class DiscountServiceTest {

    @Autowired
    private IDiscountService service;

    private Discount discount;

    @BeforeEach
    void setup() {
        discount = DiscountFactory.createDiscount(
                "SUMMER25" + UUID.randomUUID(),
                new BigDecimal("25"),
                LocalDate.of(2025, 8, 1),
                LocalDate.of(2025, 8, 31)
        );
    }

    @Test
    @Order(1)
    void create() {
        Discount newDiscount = service.create(discount);
        assertNotNull(newDiscount);
        System.out.println(newDiscount);
    }

    @Test
    @Order(2)
    void read() {
        Discount read = service.read(discount.getDiscountId());
        assertNotNull(read);
        System.out.print(read);
    }

    @Test
    @Order(3)
    void update() {
        Discount newDiscount = new Discount.Builder().copy(discount).setCode("Summer25" +UUID.randomUUID()).build();
        Discount updated = service.update(newDiscount);
        assertNotNull(updated);
        System.out.println(updated);
    }

    @Test
    void delete() {
    }

    @Test
    void getAll() {
        System.out.println(service.getAll());
    }
}