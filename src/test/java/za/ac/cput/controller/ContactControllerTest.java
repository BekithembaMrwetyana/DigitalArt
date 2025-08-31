package za.ac.cput.controller;

import za.ac.cput.domain.Contact;
import za.ac.cput.factory.ContactFactory;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.MethodName.class)
class ContactControllerTest {

    private static Contact contact;

    @Autowired
    private TestRestTemplate restTemplate;

    private static final String BASE_URL = "http://localhost:8080/ADP_Capstone_Project/contact";

    @BeforeAll
    public static void setup() {
        contact = ContactFactory.createContact("Ethan", "Hunt", "0791234567", "ethan.hunt@example.com");
    }

    @Test
    @Order(1)
    void a_create() {
        String url = BASE_URL + "/create";
        ResponseEntity<Contact> postResponse = this.restTemplate.postForEntity(url, contact, Contact.class);
        assertNotNull(postResponse);
        Contact savedContact = postResponse.getBody();
        assertNotNull(savedContact);
        System.out.println("Created: " + savedContact);
    }

    @Test
    @Order(2)
    void b_read() {
        String url = BASE_URL + "/read/" + contact.getContactId();
        ResponseEntity<Contact> response = this.restTemplate.getForEntity(url, Contact.class);
        assertNotNull(response.getBody());
        assertEquals(contact.getContactId(), response.getBody().getContactId());
        System.out.println("Read: " + response.getBody());
    }

    @Test
    @Order(3)
    void c_update() {
        Contact updatedContact = new Contact.Builder()
                .copy(contact)
                .setPhoneNumber("0787654321")
                .build();

        String url = BASE_URL + "/update";
        this.restTemplate.put(url, updatedContact);

        ResponseEntity<Contact> response = this.restTemplate.getForEntity(BASE_URL + "/read/" + contact.getContactId(), Contact.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        System.out.println("Updated: " + response.getBody());
    }

    @Test
    @Order(4)
    void d_delete() {
        String url = BASE_URL + "/delete/" + contact.getContactId();
        this.restTemplate.delete(url);

        ResponseEntity<Contact> response = this.restTemplate.getForEntity(BASE_URL + "/read/" + contact.getContactId(), Contact.class);
        // Depending on controller handling, it might return 404 or null body
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        System.out.println("Deleted contact with ID: " + contact.getContactId());
    }

    @Test
    @Order(5)
    void e_getAll() {
        String url = BASE_URL + "/getAll";
        ResponseEntity<Contact[]> response = this.restTemplate.getForEntity(url, Contact[].class);
        assertNotNull(response.getBody());
        System.out.println("All Contacts: ");
        for (Contact c : response.getBody()) {
            System.out.println(c);
        }
    }
}
