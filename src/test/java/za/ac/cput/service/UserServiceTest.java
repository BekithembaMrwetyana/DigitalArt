package za.ac.cput.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.ac.cput.domain.User;
import za.ac.cput.factory.UserFactory;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
class UserServiceTest {

    @Autowired
    private static IUserService service;

    private static User user;

    @BeforeAll
    static void setUp() {
        user = UserFactory.createUser("Doe", "John", "Password123", "john.doe@example.com", "0741234567", "0219876543");
    }

    @Test
    @Order(1)
    void a_create() {
        User created = service.create(user);
        assertNotNull(created);
        user = created;
        System.out.println("Created: " + created);
    }

    @Test
    @Order(2)
    void b_read() {
        User read = service.read(user.getUserId());
        assertNotNull(read);
        assertEquals(user.getUserId(), read.getUserId());
        System.out.println("Read: " + read);
    }

    @Test
    @Order(3)
    void c_update() {
        User updatedUser = new User.Builder()
                .copy(user)
                .setLastLogin(LocalDateTime.now())
                .setFirstName("Jonathan")
                .build();

        User updated = service.update(updatedUser);
        assertNotNull(updated);
        System.out.println("Updated: " + updated);
    }

    @Test
    @Order(4)
    void d_delete() {
//        boolean deleted = service.delete(user.getUserId());
//        assertTrue(deleted);
//        System.out.println("Deleted User ID: " + user.getUserId());
    }

    @Test
    @Order(5)
    void e_getAll() {
        List<User> allUsers = service.getAll();
        assertNotNull(allUsers);
        System.out.println("All Users: " + allUsers);
    }
}
