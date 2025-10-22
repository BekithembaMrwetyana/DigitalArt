package za.ac.cput.service;


import org.springframework.beans.factory.annotation.Autowired;
import za.ac.cput.domain.Category;
import za.ac.cput.domain.Notification;
import za.ac.cput.domain.User;
import za.ac.cput.repository.NotificationRepository;

import java.util.List;

public interface INotificationService extends IService<Notification, Long> {

    List<Notification> getAll();

    List<Notification> getAllByUser(Long userId);
}