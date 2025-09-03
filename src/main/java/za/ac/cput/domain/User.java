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
    private LocalDateTime lastLogin;
    private LocalDate createDate;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String email;
    private String phoneNumber;
    private String altNumber;

    protected User() {}

    private User(Builder builder) {
        this.userId = builder.userId;
        this.lastName = builder.lastName;
        this.firstName = builder.firstName;
        this.password = builder.password;
        this.role = builder.role;
        this.lastLogin = builder.lastLogin;
        this.createDate = builder.createDate;
        this.email = builder.email;
        this.phoneNumber = builder.phoneNumber;
        this.altNumber = builder.altNumber;
    }

    public Long getUserId() { return userId; }
    public String getLastName() { return lastName; }
    public String getFirstName() { return firstName; }
    public String getPassword() { return password; }
    public Role getRole() { return role; }
    public LocalDateTime getLastLogin() { return lastLogin; }
    public LocalDate getCreateDate() { return createDate; }
    public String getEmail() { return email; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getAltNumber() { return altNumber; }

    public void setUserId(Long userId) { this.userId = userId; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setPassword(String password) { this.password = password; }
    public void setRole(Role role) { this.role = role; }
    public void setLastLogin(LocalDateTime lastLogin) { this.lastLogin = lastLogin; }
    public void setCreateDate(LocalDate createDate) { this.createDate = createDate; }
    public void setEmail(String email) { this.email = email; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public void setAltNumber(String altNumber) { this.altNumber = altNumber; }

    public static class Builder {
        private Long userId;
        private String lastName;
        private String firstName;
        private String password;
        private Role role;
        private LocalDateTime lastLogin;
        private LocalDate createDate;
        private String email;
        private String phoneNumber;
        private String altNumber;

        public Builder setUserId(Long userId) { this.userId = userId; return this; }
        public Builder setLastName(String lastName) { this.lastName = lastName; return this; }
        public Builder setFirstName(String firstName) { this.firstName = firstName; return this; }
        public Builder setPassword(String password) { this.password = password; return this; }
        public Builder setRole(Role role) { this.role = role; return this; }
        public Builder setLastLogin(LocalDateTime lastLogin) { this.lastLogin = lastLogin; return this; }
        public Builder setCreateDate(LocalDate createDate) { this.createDate = createDate; return this; }
        public Builder setEmail(String email) { this.email = email; return this; }
        public Builder setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; return this; }
        public Builder setAltNumber(String altNumber) { this.altNumber = altNumber; return this; }

        public Builder copy(User user) {
            this.userId = user.userId;
            this.lastName = user.lastName;
            this.firstName = user.firstName;
            this.password = user.password;
            this.role = user.role;
            this.lastLogin = user.lastLogin;
            this.createDate = user.createDate;
            this.email = user.email;
            this.phoneNumber = user.phoneNumber;
            this.altNumber = user.altNumber;
            return this;
        }

        public User build() { return new User(this); }
    }
}
