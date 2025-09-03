package za.ac.cput.factory;

import za.ac.cput.domain.User;
import za.ac.cput.domain.enums.Role;
import za.ac.cput.util.Helper;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class UserFactory {

    public static User createUser(String lastName, String firstName, String password, LocalDateTime lastLogin, LocalDate createDate, Role role, String email, String phoneNumber, String altNumber) {

        if (Helper.isNullOrEmpty(firstName) || Helper.isNullOrEmpty(lastName) || Helper.isNullOrEmpty(password)) {
            return null;
        }

        if (Helper.isNullOrEmpty(email) || Helper.isNullOrEmpty(phoneNumber) || phoneNumber.length() != 10) {
            return null;
        }

        return new User.Builder()
                .setLastName(lastName)
                .setFirstName(firstName)
                .setPassword(password)
                .setRole(role)
                .setLastLogin(lastLogin)
                .setCreateDate(createDate)
                .setEmail(email)
                .setPhoneNumber(phoneNumber)
                .setAltNumber(altNumber)
                .build();
    }
}
