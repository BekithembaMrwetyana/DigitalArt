package za.ac.cput.factory;

import za.ac.cput.domain.User;
import za.ac.cput.domain.enums.Role;
import za.ac.cput.util.Helper;

public class UserFactory {

    public static User createUser(String firstName, String lastName, String password, Role role, String email, String phoneNumber) {
        if (Helper.isNullOrEmpty(firstName) || Helper.isNullOrEmpty(lastName) || Helper.isNullOrEmpty(password)) {
            return null;
        }
        if (Helper.isNullOrEmpty(email) || Helper.isNullOrEmpty(phoneNumber) || phoneNumber.length() != 10) {
            return null;
        }
        return new User.Builder()
                .setFirstName(firstName)
                .setLastName(lastName)
                .setPassword(password)
                .setRole(role)
                .setEmail(email)
                .setPhoneNumber(phoneNumber)
                .build();
    }
}
