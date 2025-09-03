package za.ac.cput.factory;


import za.ac.cput.domain.Contact;
import za.ac.cput.domain.User;
import za.ac.cput.domain.enums.Role;
import za.ac.cput.util.Helper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Locale;

public class UserFactory {

    public static User createUser(String lastName, String firstName, String password, LocalDateTime lastLogin, LocalDate createDate, Role role, String email, String phoneNumber, String altNumber) {

        if (Helper.isNullOrEmpty(firstName) || Helper.isNullOrEmpty(lastName) || Helper.isNullOrEmpty(password)) {
            return null;        }
        Contact contact = ContactFactory.createContact(phoneNumber, email, LocalDate.now() ,altNumber);
        return new User.Builder()
                .setLastName(lastName)
                .setFirstName(firstName)
                .setPassword(password)
                .setLastLogin(lastLogin)
                .setCreateDate(createDate)
                .setRole(role)
                .setContact(contact)
                .build();
    }
}