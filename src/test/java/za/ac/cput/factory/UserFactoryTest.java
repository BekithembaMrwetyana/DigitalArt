package za.ac.cput.factory;

import org.junit.jupiter.api.Test;
import za.ac.cput.domain.User;
import za.ac.cput.domain.enums.Role;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class UserFactoryTest {

    @Test
    void createUserSuccess() {
        User user = UserFactory.createUser(
                "Doe",
                "John",
                "Password123",
                LocalDateTime.now(),
                LocalDate.now(),
                Role.ADMIN,
                "johndoe@example.com",
                "0123456789",
                "0987654321"
        );

        assertNotNull(user);
        assertEquals("Doe", user.getLastName());
        assertEquals("John", user.getFirstName());
        assertEquals("Password123", user.getPassword());
        assertEquals(Role.ADMIN, user.getRole());
        assertEquals("johndoe@example.com", user.getEmail());
        assertEquals("0123456789", user.getPhoneNumber());
        assertEquals("0987654321", user.getAltNumber());
    }

    @Test
    void createUserFailsNullValues() {
        User user = UserFactory.createUser(
                null,
                "",
                null,
                LocalDateTime.now(),
                LocalDate.now(),
                Role.CUSTOMER,
                "",
                "",
                ""
        );
        assertNull(user);
    }

    @Test
    void createUserFailsShortPhone() {
        User user = UserFactory.createUser(
                "Smith",
                "Alice",
                "pass123",
                LocalDateTime.now(),
                LocalDate.now(),
                Role.CUSTOMER,
                "alice@example.com",
                "1234",
                ""
        );
        assertNull(user);
    }
}
