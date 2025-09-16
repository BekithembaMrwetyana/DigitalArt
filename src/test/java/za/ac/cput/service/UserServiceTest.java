package za.ac.cput.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.ac.cput.domain.User;
import za.ac.cput.domain.enums.Role;
import za.ac.cput.factory.UserFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserServiceTest {

    @Autowired
    private IUserService service;

    private static User user = UserFactory.createUser(
            "Doe",
            "John",
            "Password123",
            Role.ADMIN,
            "johndoe123@gmail.com",
            "0789037859"
    );

    @BeforeAll
    static void setUp() {
        user = UserFactory.createUser(
                "Doe",
                "John",
                "Password123",
                Role.ADMIN,
                "johndoe123@gmail.com",
                "0789037859"
        );
        assertNotNull(user);
    }

    @Test
    @Order(1)
    void a_create() {
        String uniqueEmail = "johndoe" + System.currentTimeMillis() + "@example.com";
        String uniquePassword = "Password" + System.currentTimeMillis();

        user = UserFactory.createUser(
                "Doe",
                "John",
                uniquePassword,
                Role.ADMIN,
                uniqueEmail,
                "0789037859"
        );

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
                .build();

        User updated = service.update(updatedUser);
        assertNotNull(updated);
        assertEquals("Jonathan", updated.getFirstName());
        user = updated;
    }

    @Test
    @Order(4)
    void d_partialUpdate() {
        User patchUser = new User.Builder()
                .copy(user)
                .setLastName("Smith")
                .build();

        User patched = service.partialUpdate(patchUser);
        assertNotNull(patched);
        assertEquals("Smith", patched.getLastName());
        user = patched;
    }

    @Test
    @Order(5)
    void e_delete() {
        service.delete(user.getUserId());
        User deletedUser = service.read(user.getUserId());
        assertNull(deletedUser);
    }

    @Test
    @Order(6)
    void f_getAll() {
        List<User> allUsers = service.getAll();
        assertNotNull(allUsers);
    }

    @Test
    @Order(7)
    void g_createDuplicatePassword() {
        String uniqueEmail = "janedoe" + System.currentTimeMillis() + "@example.com";
        Exception exception = assertThrows(IllegalArgumentException.class, () -> service.create(UserFactory.createUser(
                "Jane",
                "Doe",
                user.getPassword(),
                Role.CUSTOMER,
                uniqueEmail,
                "0790000000"
        )));
        assertEquals("Password is already in use", exception.getMessage());
    }
}
