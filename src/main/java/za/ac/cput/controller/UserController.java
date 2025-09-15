package za.ac.cput.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.domain.User;
import za.ac.cput.dto.LoginRequest;
import za.ac.cput.dto.UserResponse;
import za.ac.cput.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public User createUser(@RequestBody User user) {
        return service.create(user);
    }

    @GetMapping("/read/{id}")
    public User getUserById(@PathVariable Long id) {
        return service.read(id);
    }

    @PutMapping("/update/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user) {
        user.setUserId(id);
        return service.update(user);
    }

    @PatchMapping("/update/{id}")
    public User partialUpdateUser(@PathVariable Long id, @RequestBody User user) {
        user.setUserId(id);
        return service.partialUpdate(user);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable Long id) {
        service.delete(id);
    }

    @GetMapping("/getAll")
    public List<User> getAll() {
        return service.getAll();
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(@RequestBody LoginRequest loginRequest) {
        User user = service.getByEmail(loginRequest.getEmail());

        if (user == null) {
            return ResponseEntity.status(404).build();
        }

        if (!user.getPassword().equals(loginRequest.getPassword())) {
            return ResponseEntity.status(401).build();
        }

        if (!user.getRole().name().equalsIgnoreCase(loginRequest.getRole())) {
            return ResponseEntity.status(403).build();
        }

        UserResponse response = new UserResponse(
                user.getUserId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getRole()
        );

        return ResponseEntity.ok(response);
    }
}
