package za.ac.cput.domain;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notificationId;
    private String title;
    private String message;
    private LocalDateTime createdAt;
    private Boolean status;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_Id")
    private User user;

    public Notification(){

    }
    private Notification(Builder builder) {
        this.notificationId = builder.notificationId;
        this.title = builder.title;
        this.message = builder.message;
        this.createdAt = builder.createdAt;
        this.status = builder.status;
        this.user = builder.user;
    }
    public Long getNotificationId () {
        return notificationId;
    }
    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Boolean getStatus() {
        return status;
    }

    public User getUser() {
        return user;
    }

    @Override
    public String toString() {
        return "NotificationFactory{" +
                "notificationId=" + notificationId +
                ", title='" + title + '\'' +
                ", message='" + message + '\'' +
                ", createdAt=" + createdAt +
                ", status=" + status +
                ", user=" + user +
                '}';
    }


    public static class Builder {
        private Long notificationId;
        private String title;
        private String message;
        private LocalDateTime createdAt;
        private boolean status;
        private User user;


        public Builder setNotificationId(Long notificationId) {
            this.notificationId = notificationId;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder setcreatedAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder setStatus(boolean status) {
            this.status = status;
            return this;
        }

        public Builder setUser(User user) {
            this.user = user;
            return this;
        }

        public Builder copy(Notification notification) {
            this.notificationId = notification.notificationId;
            this.title = notification.title;
            this.message = notification.message;
            this.createdAt = notification.createdAt;
            this.status = notification.status;
            this.user = notification.user;
            return this;
        }

        public Notification build() {
            return new Notification(this); }
    }

}