package za.ac.cput.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.ac.cput.domain.User;
import za.ac.cput.domain.enums.Role;
import za.ac.cput.dto.LoginRequest;
import za.ac.cput.factory.UserFactory;
import za.ac.cput.service.UserService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserControllerTest {

    @Autowired
    private UserService service;

    private UserController controller;
    private User user;

    @BeforeAll
    void setup() {
        controller = new UserController(service);
        user = UserFactory.createUser(
                "Jon",
                "Doe",
                "doe123",
                Role.CUSTOMER,
                "jon_controller@example.com",
                "0123456789"
        );
    }

    @Test
    @Order(1)
    void a_create() {
        User created = controller.createUser(user);
        assertNotNull(created);
        assertNotNull(created.getUserId());
        user = created;
    }

    @Test
    @Order(2)
    void b_read() {
        User read = controller.getUserById(user.getUserId());
        assertNotNull(read);
        assertEquals(user.getUserId(), read.getUserId());
    }

    @Test
    @Order(3)
    void c_update() {
        User updatedUser = new User.Builder()
                .copy(user)
                .setLastName("Johnson")
                .build();

        User updated = controller.updateUser(user.getUserId(), updatedUser);
        assertNotNull(updated);
        assertEquals("Johnson", updated.getLastName());
        user = updated;
    }

    @Test
    @Order(4)
    void d_delete() {
        controller.deleteUser(user.getUserId());
        User deleted = controller.getUserById(user.getUserId());
        assertNull(deleted);
    }

    @Test
    @Order(5)
    void e_getAll() {
        List<User> allUsers = controller.getAll();
        assertNotNull(allUsers);
    }

    @Test
    @Order(6)
    void f_login() {
        String uniqueEmail = "alice" + System.currentTimeMillis() + "@example.com";

        User loginUser = controller.createUser(UserFactory.createUser(
                "Alice",
                "Smith",
                "alice123",
                Role.CUSTOMER,
                uniqueEmail,
                "0987654321"
        ));

        LoginRequest loginRequest = new LoginRequest(uniqueEmail, "alice123");
        User loggedIn = controller.login(loginRequest);
        assertNotNull(loggedIn);
        assertEquals("Alice", loggedIn.getFirstName());
    }
}
