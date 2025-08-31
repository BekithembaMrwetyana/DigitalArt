package za.ac.cput.service;

import org.springframework.beans.factory.annotation.Autowired;
import za.ac.cput.domain.Contact;
import za.ac.cput.factory.ContactFactory;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
class ContactServiceTest {

    @Autowired
    private static IContactService service;

    private static Contact contact;

    @BeforeAll
    static void setUp() {
        contact = ContactFactory.createContact("Oliver", "Stone", "0711112222");

    }

    @Test
    @Order(1)
    void a_create() {
        Contact created = service.create(contact);
        assertNotNull(created);
        System.out.println("Created: " + created);
    }

    @Test
    @Order(2)
    void b_read() {
        Contact read = service.read(contact.getContactId());
        assertNotNull(read);
        System.out.println("Read: " + read);
    }

    @Test
    @Order(3)
    void c_update() {
        Contact updatedContact = new Contact.Builder()
                .copy(contact)
                .setPhoneNumber("0722223333")
                .build();
        Contact updated = service.update(updatedContact);
        assertNotNull(updated);
        System.out.println("Updated: " + updated);
    }

    @Test
    @Order(4)
    void d_delete() {
//        void deleted = service.delete(contact.getContactId());
//        assertTrue(deleted);
//        System.out.println("Deleted contact with ID: " + contact.getContactId());
    }

    @Test
    @Order(5)
    void e_getAll() {
        List<Contact> allContacts = service.getAll();
        assertNotNull(allContacts);
        System.out.println("All Contacts: " + allContacts);
    }
}
