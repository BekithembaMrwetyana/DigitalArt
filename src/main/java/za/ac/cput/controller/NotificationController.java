package za.ac.cput.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.domain.Notification;
import za.ac.cput.service.NotificationService;

import java.util.List;

@RestController
@RequestMapping("/notifications")
@CrossOrigin(origins = "http://localhost:5173")
public class NotificationController {

    private NotificationService service;

    @Autowired
    public NotificationController(NotificationService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public Notification create(@RequestBody Notification notification) {
        return service.create(notification);
    }

    @GetMapping("/read/{notificationId}")
    public Notification read(@PathVariable Long notificationId) {
        return service.read(notificationId);
    }

    @PutMapping("/update")
    public Notification update(@RequestBody Notification notification) {
        return service.update(notification);
    }

    @DeleteMapping("/delete/{notificationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long notificationId) {
        service.delete(notificationId);
    }

    @GetMapping("/getAll")
    public List<Notification> getAll() {
        return service.getAll();
    }

    @GetMapping("/getAll/{userId}")
    public List<Notification> getAllByUser(@PathVariable Long userId) {
        return service.getAllByUser(userId);
    }
    @PutMapping("/{notificationId}/mark-as-read")
    public Notification markAsRead(@PathVariable Long notificationId) {
        Notification notification = service.read(notificationId);

        // Use the Builder pattern to create an updated notification
        Notification updatedNotification = new Notification.Builder()
                .copy(notification)
                .setStatus(true)
                .build();

        return service.update(updatedNotification);
    }
}