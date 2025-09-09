package za.ac.cput.domain;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import za.ac.cput.domain.enums.Role;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String lastName;
    private String firstName;
    private String password;


    @Enumerated(EnumType.STRING)
    private Role role;

    private String email;
    private String phoneNumber;

    protected User() {}

    private User(Builder builder) {
        this.userId = builder.userId;
        this.lastName = builder.lastName;
        this.firstName = builder.firstName;
        this.password = builder.password;
        this.role = builder.role;
        this.email = builder.email;
        this.phoneNumber = builder.phoneNumber;
    }

    public Long getUserId() { return userId; }
    public String getLastName() { return lastName; }
    public String getFirstName() { return firstName; }
    public String getPassword() { return password; }
    public Role getRole() { return role; }
    public String getEmail() { return email; }
    public String getPhoneNumber() { return phoneNumber; }

    public void setUserId(Long userId) { this.userId = userId; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setPassword(String password) { this.password = password; }
    public void setRole(Role role) { this.role = role; }
    public void setEmail(String email) { this.email = email; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public static class Builder {
        private Long userId;
        private String firstName;
        private String lastName;
        private String password;
        private Role role;
        private String email;
        private String phoneNumber;

        public Builder setUserId(Long userId) { this.userId = userId; return this; }
        public Builder setFirstName(String firstName) { this.firstName = firstName; return this; }
        public Builder setLastName(String lastName) { this.lastName = lastName; return this; }
        public Builder setPassword(String password) { this.password = password; return this; }
        public Builder setRole(Role role) { this.role = role; return this; }
        public Builder setEmail(String email) { this.email = email; return this; }
        public Builder setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; return this; }

        public Builder copy(User user) {
            this.userId = user.userId;
            this.firstName = user.firstName;
            this.lastName = user.lastName;
            this.password = user.password;
            this.role = user.role;
            this.email = user.email;
            this.phoneNumber = user.phoneNumber;
            return this;
        }

        public User build() { return new User(this); }
    }
}