package za.ac.cput.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import za.ac.cput.domain.Notification;
import za.ac.cput.domain.User;
import za.ac.cput.domain.enums.Role;
import za.ac.cput.factory.NotificationFactory;
import za.ac.cput.factory.UserFactory;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.MethodName.class)
class NotificationControllerTest {

    private static Notification notification;
    private static User user;

    @Autowired
    private TestRestTemplate restTemplate;

    private static final String BASE_URL = "/notifications";

    @BeforeAll
    public static void setup() {
        user = UserFactory.createUser(
                "Mbali", "Gabriel", "passwo333",
                Role.CUSTOMER, "Mbali@gmail.com", "0755189641");

        notification = NotificationFactory.createNotification(
                "Test Title", "Test Message", LocalDateTime.now(), false, user);
    }

    @Test
    @Order(1)
    void a_create() {
        String url = BASE_URL + "/create";
        Notification createdNotification = this.restTemplate.postForObject(url, notification, Notification.class);
        assertNotNull(createdNotification);
        assertEquals(notification.getTitle(), createdNotification.getTitle());
        notification = createdNotification;
        System.out.println("Created: " + createdNotification);
    }

    @Test
    @Order(2)
    void b_read() {
        String url = BASE_URL + "/read/" + notification.getNotificationId();
        ResponseEntity<Notification> response = this.restTemplate.getForEntity(url, Notification.class);
        assertNotNull(response.getBody());
        assertEquals(notification.getNotificationId(), response.getBody().getNotificationId());
        System.out.println("Read: " + response.getBody());
    }

    @Test
    @Order(3)
    void c_update() {
        Notification updatedNotification = new Notification.Builder().copy(notification)
                .setTitle("Updated Title")
                .setMessage("Updated Message")
                .setStatus(true)
                .build();

        HttpEntity<Notification> request = new HttpEntity<>(updatedNotification);
        ResponseEntity<Notification> response = restTemplate.exchange(
                BASE_URL + "/update",
                HttpMethod.PUT,
                request,
                Notification.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Updated Title", response.getBody().getTitle());
        assertEquals("Updated Message", response.getBody().getMessage());
        assertTrue(response.getBody().getStatus());

        notification = response.getBody();
        System.out.println("Updated: " + notification);
    }
    @Test
    @Order(4)
    void d_markAsRead() {
        String url = BASE_URL + "/" + notification.getNotificationId() + "/mark-as-read";

        HttpEntity<Void> request = new HttpEntity<>(null);
        ResponseEntity<Notification> response = restTemplate.exchange(
                url,
                HttpMethod.PUT,
                request,
                Notification.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().getStatus(), "Status should be true after marking as read");

        notification = response.getBody();
        System.out.println("Marked as read: " + notification);
    }

    @Test
    @Order(5)
    void d_getAll() {
        String url = BASE_URL + "/getAll";
        ResponseEntity<Notification[]> response = this.restTemplate.getForEntity(url, Notification[].class);
        assertNotNull(response.getBody());
        assertTrue(response.getBody().length > 0, "Should return at least one notification");
        System.out.println("Get All:");
        for (Notification n : response.getBody()) {
            System.out.println(n);
        }
    }

    @Test
    @Order(6)
    void e_delete() {
        String url = BASE_URL + "/delete/" + notification.getNotificationId();
        ResponseEntity<Void> response = restTemplate.exchange(url, HttpMethod.DELETE, null, Void.class);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        System.out.println("Deleted notification with ID: " + notification.getNotificationId());
    }
}