package za.ac.cput.factory;

import za.ac.cput.domain.Notification;
import za.ac.cput.domain.User;
import za.ac.cput.util.Helper;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class NotificationFactory {

    public static Notification createNotification(String title,
                                                  String message,
                                                  LocalDateTime createdAt,
                                                  boolean status,
                                                  User user ) { //add attributes of the user instead of user


        if (Helper.isNullOrEmpty(title) || Helper.isNullOrEmpty(message)){
            return null;
        }

        return new Notification.Builder()
                .setTitle(title)
                .setMessage(message)
                .setcreatedAt(createdAt)
                .setStatus(status)
                .setUser(user)
                .build();
    }
}