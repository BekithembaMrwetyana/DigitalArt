package factory;

import domain.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserFactoryTest {

    private static User u1 = UserFactory.createUser(202501, "Nana", "Lucas", "", "");
    private static User u2 = UserFactory.createUser(202502, "Mbali", "Precious", "PreciousMbali@gmail.com", "Mbali@20002");
    private static User u3 = UserFactory.createUser(202503, "Steers", "Goodman", "goodman.com", "Steers@9120");


    @Test
    public void testCreateUser() {
        assertNotNull(u1);
        System.out.println(u1.toString());
    }

    @Test
    public void testCreateUserWithAllAttributes() {
        assertNotNull(u2);
        System.out.println(u2.toString());
    }

    @Test
    public void testCreateUserThatFails() {
        assertNotNull(u3);
        System.out.println(u1.toString());
    }
}
