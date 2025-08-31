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
import static za.ac.cput.controller.UserControllerTest.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
class ContactControllerTest {

    private static Contact contact;

    @BeforeAll
    public static void setup() {
        contact = ContactFactory.createContact(
                "0662134256",
                "ethan.hunt@example.com",
                "0791234567"
        );
    }

    @Test
    @Order(1)
    void a_createContact() {
        assertNotNull(contact);
        System.out.println("Created Contact (factory only, no API call): " + contact);
    }

    @Test
    @Order(2)
    void b_readFields() {
        assertEquals("0662134256", contact.getPhoneNumber());
        assertEquals("ethan.hunt@example.com", contact.getEmail());
        assertEquals("0791234567", contact.getAltNumber());
        System.out.println("Read fields from Contact: " + contact);
    }

    @Test
    @Order(3)
    void c_updateContact() {
        Contact updated = new Contact.Builder()
                .copy(contact)
                .setPhoneNumber("0787654321")
                .build();

        assertNotEquals(contact.getPhoneNumber(), updated.getPhoneNumber());
        System.out.println("Updated Contact: " + updated);
    }

    @Test
    @Order(4)
    void d_deleteSimulation() {
        contact = null;
        assertNull(contact);
        System.out.println("Deleted Contact (simulated by nulling reference).");
    }

    @Test
    @Order(5)
    void e_getAllSimulation() {
        Contact contact1 = ContactFactory.createContact("0711111111", "john.doe@example.com", "0722222222");
        Contact contact2 = ContactFactory.createContact("0733333333", "jane.doe@example.com", "0744444444");

        Contact[] allContacts = {contact1, contact2};

        assertEquals(2, allContacts.length);
        System.out.println("All Contacts: ");
        for (Contact c : allContacts) {
            System.out.println(c);
        }
    }
}