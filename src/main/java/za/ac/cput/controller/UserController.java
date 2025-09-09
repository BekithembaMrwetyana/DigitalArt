package za.ac.cput.controller;

import za.ac.cput.domain.User;
import za.ac.cput.dto.LoginRequest;
import za.ac.cput.service.UserService;
import java.util.List;

public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    public User createUser(User user) {
        return service.create(user);
    }

    public User getUserById(Long id) {
        return service.read(id);
    }

    public User updateUser(Long id, User user) {
        user.setUserId(id);
        return service.update(user);
    }

    public void deleteUser(Long id) {
        service.delete(id);
    }

    public List<User> getAll() {
        return service.getAll();
    }

    public User login(LoginRequest loginRequest) {
        User user = service.getByEmail(loginRequest.getEmail());
        if (user != null && user.getPassword().equals(loginRequest.getPassword())) {
            return user;
        }
        return null;
    }
}
