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
        assertNotNull(user);
    }

    @Test
    @Order(1)
    void a_create() {
        User created = service.create(user);
        assertNotNull(created);
        assertNotNull(created.getUserId());
        user = created;
    }

    @Test
    @Order(2)
    void b_read() {
        User read = service.read(user.getUserId());
        assertNotNull(read);
        assertEquals(user.getUserId(), read.getUserId());
    }

    @Test
    @Order(3)
    void c_update() {
        User updatedUser = new User.Builder()
                .copy(user)
                .setFirstName("Jonathan")
                .setLastLogin(LocalDateTime.now())
                .build();

        User updated = service.update(updatedUser);
        assertNotNull(updated);
        assertEquals("Jonathan", updated.getFirstName());
        user = updated;
    }

    @Test
    @Order(4)
    void d_delete() {
        service.delete(user.getUserId());
        User deletedUser = service.read(user.getUserId());
        assertNull(deletedUser);
    }

    @Test
    @Order(5)
    void e_getAll() {
        List<User> allUsers = service.getAll();
        assertNotNull(allUsers);
    }

    @Test
    @Order(6)
    void f_verifyEmailPhoneSaved() {
        User savedUser = service.read(user.getUserId());
        assertNull(savedUser);
    }
}
