package za.ac.cput.factory;

import za.ac.cput.domain.Contact;
import za.ac.cput.util.Helper;

import java.time.LocalDate;

public class ContactFactory {

    public static Contact createContact(String phoneNumber, String email, LocalDate createDate, String altNumber) {

        if (Helper.isNullOrEmpty(email) || Helper.isNullOrEmpty(phoneNumber)) {
            return null;

        }
        if (phoneNumber.length() != 11) {
            return null;
        }

        return new Contact.Builder()
                .setPhoneNumber(phoneNumber)
                .setEmail(email)
                .setCreateDate(LocalDate.now())
                .setAltNumber(altNumber)
                .build();
    }
}