package za.ac.cput.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.ac.cput.domain.Notification;
import za.ac.cput.domain.enums.Role;
import za.ac.cput.domain.User;
import za.ac.cput.factory.NotificationFactory;
import za.ac.cput.factory.UserFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
class NotificationServiceTest {

    @Autowired
    private NotificationService notificationService;
    @Autowired
    private UserService userService;

    private static Notification notification1;
    private static Notification notification2;

    private static User user1;
    private static User user2;

    @Order(1)
    @Test
    void a_setup() {

        user1 = UserFactory.createUser(
                "Mbali", "Gabriel", "passwo333",
                Role.CUSTOMER, "GMbali@gmail.com", "0755189641");
        user1 = userService.create(user1);
        user2 = UserFactory.createUser(
                "Thimna", "Beki", "passwor331",
                Role.ADMIN, "Thimnaa@gmail.com", "0712316884");
        user2 = userService.create(user2); // Ensure user2 is saved

        notification1 = NotificationFactory.createNotification(
                "Test Message noti", "Test Message 5", LocalDateTime.now(), false, user1);
        assertNotNull(notification1);

        notification2 = NotificationFactory.createNotification(
                "Test Message 4", "Test Message notification", LocalDateTime.now(), true, user2);
        assertNotNull(notification2);
        System.out.println("Role being saved: " + user1.getRole());

        notificationService.create(notification1);
        notificationService.create(notification2);
    }

    @Order(2)
    @Test
    void b_save() {
        Notification created1 = notificationService.create(notification1);
        assertNotNull(created1);
//        assertEquals("Test Message 1", created1.getMessage());
//        assertEquals("Test Message 1", created1.getMessage());
        assertEquals(user1.getUserId(), created1.getUser().getUserId());
        System.out.println(created1);

        Notification created2 = notificationService.create(notification2);
        assertNotNull(created2);
//        assertEquals("Test Message 2", created2.getMessage());
//        assertEquals("Test Message 2", created2.getMessage());
        assertEquals(user2.getUserId(), created2.getUser().getUserId());
        System.out.println(created2);
    }

    @Order(3)
    @Test
    void c_read() {
        Notification read = notificationService.read(notification2.getNotificationId());
        assertNotNull(read);
        System.out.println(read);
    }

    @Order(4)
    @Test
    void d_update() {
        Notification newNotification = new Notification.Builder()
                .copy(notification2)
                .setMessage("Updated Message")
                .build();
        Notification updated = notificationService.update(newNotification);
        assertNotNull(updated);
        assertEquals("Updated Message", updated.getMessage());
        System.out.println(updated);
    }

    @Order(5)
    @Test
    void e_getAll() {
        System.out.println(notificationService.getAll());
    }

    @Disabled
    @Order(6)
    @Test
    void f_delete() {
        notificationService.delete(notification1.getNotificationId());
        assertNull(notificationService.read(notification1.getNotificationId()));
    }
}