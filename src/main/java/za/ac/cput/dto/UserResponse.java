package za.ac.cput.dto;

import lombok.Getter;
import lombok.Setter;
import za.ac.cput.domain.enums.Role;

@Setter
@Getter
public class UserResponse {
    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private Role role;
    private String token;

    public UserResponse(Long userId, String firstName, String lastName,
                        String email, String phoneNumber, Role role, String token) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.token = token;



     }
}
