package za.ac.cput.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import za.ac.cput.domain.User;
import za.ac.cput.domain.enums.Role;
import za.ac.cput.dto.LoginRequest;
import za.ac.cput.dto.UserResponse;
import za.ac.cput.factory.UserFactory;
import za.ac.cput.service.UserService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserControllerTest {

    @Autowired
    private UserService service;

    private UserController controller;
    private static User user;

    @BeforeAll
    static void init() {
        user = UserFactory.createUser(
                "Jon",
                "Doe",
                "doe123",
                Role.CUSTOMER,
                "jon_controller@example.com",
                "0123456789"
        );
    }

    @BeforeEach
    void setup() {
        controller = new UserController(service);
    }

    @Test
    @Order(1)
    void a_create() {
        ResponseEntity<?> response = controller.createUser(user);
        assertEquals(200, response.getStatusCodeValue());
        User created = (User) response.getBody();
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
    void d_partialUpdate() {
        User patchUser = new User.Builder()
                .copy(user)
                .setFirstName("Johnny")
                .build();

        User patched = controller.partialUpdateUser(user.getUserId(), patchUser);
        assertNotNull(patched);
        assertEquals("Johnny", patched.getFirstName());
        user = patched;
    }

    @Test
    @Order(5)
    void e_delete() {
        controller.deleteUser(user.getUserId());
        User deleted = controller.getUserById(user.getUserId());
        assertNull(deleted);
    }

    @Test
    @Order(6)
    void f_getAll() {
        List<User> allUsers = controller.getAll();
        assertNotNull(allUsers);
    }

    @Test
    @Order(7)
    void g_login() {
        String uniqueEmail = "alice" + System.currentTimeMillis() + "@example.com";

        controller.createUser(UserFactory.createUser(
                "Alice",
                "Smith",
                "alice123",
                Role.CUSTOMER,
                uniqueEmail,
                "0987654321"
        ));

        LoginRequest loginRequest = new LoginRequest(uniqueEmail, "alice123", "CUSTOMER");
        ResponseEntity<UserResponse> response = controller.login(loginRequest);
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        UserResponse loggedIn = response.getBody();
        assertNotNull(loggedIn);
        assertEquals(uniqueEmail, loggedIn.getEmail());
        assertEquals(Role.CUSTOMER, loggedIn.getRole());
    }

    @Test
    @Order(8)
    void h_login_wrongPassword() {
        String uniqueEmail = "bob" + System.currentTimeMillis() + "@example.com";

        controller.createUser(UserFactory.createUser(
                "Bob",
                "Marley",
                "bob123",
                Role.CUSTOMER,
                uniqueEmail,
                "0112233445"
        ));

        LoginRequest loginRequest = new LoginRequest(uniqueEmail, "wrongpass", "CUSTOMER");
        ResponseEntity<UserResponse> response = controller.login(loginRequest);
        assertEquals(401, response.getStatusCodeValue());
    }

    @Test
    @Order(9)
    void i_login_wrongRole() {
        String uniqueEmail = "carol" + System.currentTimeMillis() + "@example.com";

        controller.createUser(UserFactory.createUser(
                "Carol",
                "Danvers",
                "carol123",
                Role.CUSTOMER,
                uniqueEmail,
                "0998877665"
        ));

        LoginRequest loginRequest = new LoginRequest(uniqueEmail, "carol123", "ADMIN");
        ResponseEntity<UserResponse> response = controller.login(loginRequest);
        assertEquals(403, response.getStatusCodeValue());
    }

    @Test
    @Order(10)
    void j_login_userNotFound() {
        LoginRequest loginRequest = new LoginRequest("nonexistent@example.com", "nopass", "CUSTOMER");
        ResponseEntity<UserResponse> response = controller.login(loginRequest);
        assertEquals(404, response.getStatusCodeValue());
    }
}
