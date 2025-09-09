package za.ac.cput.factory;

import org.junit.jupiter.api.Test;
import za.ac.cput.domain.User;
import za.ac.cput.domain.enums.Role;

import static org.junit.jupiter.api.Assertions.*;

class UserFactoryTest {

    @Test
    void createUserSuccess() {
        User user = UserFactory.createUser(
                "John",
                "Doe",
                "Password123",
                Role.ADMIN,
                "johndoe@example.com",
                "0123456789"
        );

        assertNotNull(user);
        assertEquals("John", user.getFirstName());
        assertEquals("Doe", user.getLastName());
        assertEquals("Password123", user.getPassword());
        assertEquals(Role.ADMIN, user.getRole());
        assertEquals("johndoe@example.com", user.getEmail());
        assertEquals("0123456789", user.getPhoneNumber());
    }

    @Test
    void createUserFailsNullValues() {
        User user = UserFactory.createUser(
                null,
                "",
                null,
                Role.CUSTOMER,
                "",
                ""
        );
        assertNull(user);
    }

    @Test
    void createUserFailsShortPhone() {
        User user = UserFactory.createUser(
                "Alice",
                "Smith",
                "pass123",
                Role.CUSTOMER,
                "alice@example.com",
                "1234"
        );
        assertNull(user);
    }
}
