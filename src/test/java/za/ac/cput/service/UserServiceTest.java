package za.ac.cput.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.ac.cput.domain.User;
import za.ac.cput.domain.enums.Role;
import za.ac.cput.factory.UserFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserServiceTest {

    @Autowired
    private IUserService service;

    private static User user;
    @BeforeAll
    static void setUp() {
        user = UserFactory.createUser(
                "Doe",
                "John",
                "Password123",
                LocalDateTime.now(),
                LocalDate.now(),
                Role.ADMIN,
                "johndoe123@gmail.com",
                "0789037859",
                "0647824672"
        );
    }

    @Test
    @Order(1)
    void a_create() {
        User created = service.create(user);
        assertNotNull(created);
        assertNotNull(created.getUserId());
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
        assertEquals("Jonathan", updated.getFirstName());
        user = updated;
        System.out.println("Updated: " + updated);
    }

    @Test
    @Order(4)
    void d_delete() {
        service.delete(user.getUserId());
        User deletedUser = service.read(user.getUserId());
        assertNull(deletedUser);
        System.out.println("Deleted User ID: " + user.getUserId());
    }

    @Test
    @Order(5)
    void e_getAll() {
        List<User> allUsers = service.getAll();
        assertNotNull(allUsers);
        System.out.println("All Users: " + allUsers);
    }
}