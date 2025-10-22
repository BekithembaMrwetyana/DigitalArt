package za.ac.cput.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.domain.User;
import za.ac.cput.dto.LoginRequest;
import za.ac.cput.dto.UserResponse;
import za.ac.cput.security.JwtUtil;
import za.ac.cput.service.UserService;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/users")
public class UserController {

    private final UserService service;
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    // ✅ FIX: Inject all required beans properly
    public UserController(UserService service, JwtUtil jwtUtil, UserDetailsService userDetailsService) {
        this.service = service;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    // ✅ Create User (Registration) — now hashes password
    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        try {
            User created = service.create(user);
            return ResponseEntity.ok(created);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
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

    // ✅ Login with password + role validation + JWT generation
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        User user = service.getByEmail(loginRequest.getEmail());

        if (user == null) {
            return ResponseEntity.status(404).body("User not found");
        }

        // ✅ Compare passwords (raw vs encoded)
        if (!service.passwordMatches(loginRequest.getPassword(), user.getPassword())) {
            return ResponseEntity.status(401).body("Invalid password");
        }

        // ✅ Check role consistency
        if (!user.getRole().name().equalsIgnoreCase(loginRequest.getRole())) {
            return ResponseEntity.status(403).body("Forbidden: Role mismatch");
        }

        // ✅ Load UserDetails and generate token
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
        String role = user.getRole().name();
        String token = jwtUtil.generateToken(user.getEmail(), role);

        // ✅ Build and return response with JWT
        UserResponse response = new UserResponse(
                user.getUserId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getRole(),
                token
        );

        return ResponseEntity.ok(response);
    }
}
