package za.ac.cput.controller;

import za.ac.cput.domain.User;
import za.ac.cput.factory.UserFactory;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.MethodName.class)
class UserControllerTest {

    private static User user;

    @Autowired
    private TestRestTemplate restTemplate;

    private static final String BASE_URL = "/user";

    @BeforeAll
    public static void setup() {
        user = UserFactory.createUser("Smith", "Alice", "Alice@2025", "alice@example.com", "0712345678", "0823456789");
    }

    @Test
    @Order(1)
    void a_create() {
        String url = BASE_URL + "/create";
        ResponseEntity<User> postResponse = this.restTemplate.postForEntity(url, user, User.class);
        assertNotNull(postResponse.getBody());
        user = postResponse.getBody(); // Update with DB ID
        System.out.println("Created: " + user);
    }

    @Test
    @Order(2)
    void b_read() {
        String url = BASE_URL + "/read/" + user.getUserId();
        ResponseEntity<User> response = this.restTemplate.getForEntity(url, User.class);
        assertEquals(user.getUserId(), response.getBody().getUserId());
        System.out.println("Read: " + response.getBody());
    }

    @Test
    @Order(3)
    void c_update() {
        User updatedUser = new User.Builder()
                .copy(user)
                .setLastName("Johnson")
                .build();

        String url = BASE_URL + "/update";
        this.restTemplate.put(url, updatedUser);

        ResponseEntity<User> response = this.restTemplate.getForEntity(BASE_URL + "/read/" + user.getUserId(), User.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        //ResponseEntity<User> response = restTemplate.exchage(url, HttpMethod.PUT, new HttEntity<>(updateUser), User.class);
        assertNotNull(response.getBody());
        System.out.println("Updated: " + response.getBody());
    }

    @Test
    @Order(4)
    void d_delete() {
        String url = BASE_URL + "/delete/" + user.getUserId();
        this.restTemplate.delete(url);

        System.out.println("Deleted User ID: " + user.getUserId());
    }

    @Test
    @Order(5)
    void e_getAll() {
        String url = BASE_URL + "/getAll";
        ResponseEntity<User[]> response = this.restTemplate.getForEntity(url, User[].class);
        assertNotNull(response.getBody());
        System.out.println("All Users: ");
        for (User u : response.getBody()) {
            System.out.println(u);
        }
    }
}
