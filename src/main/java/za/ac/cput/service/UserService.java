package za.ac.cput.service;

import org.springframework.stereotype.Service;
import za.ac.cput.domain.User;
import za.ac.cput.domain.enums.Role;
import za.ac.cput.repository.UserRepository;
import java.util.List;

@Service
public class UserService implements IUserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public User create(User user) {
        User existingUser = getByEmail(user.getEmail());
        if (existingUser != null) {
            throw new IllegalArgumentException("Email is already in use: " + user.getEmail());
        }
        if (user.getRole() == null) {
            user.setRole(Role.CUSTOMER);
        }
        return repository.save(user);
    }

    @Override
    public User read(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public User update(User user) {
        return repository.save(user);
    }

    public User partialUpdate(User user) {
        User existing = read(user.getUserId());
        if (existing == null) return null;

        if (user.getFirstName() != null) existing.setFirstName(user.getFirstName());
        if (user.getLastName() != null) existing.setLastName(user.getLastName());
        if (user.getEmail() != null) existing.setEmail(user.getEmail());
        if (user.getPhoneNumber() != null) existing.setPhoneNumber(user.getPhoneNumber());
        if (user.getPassword() != null) existing.setPassword(user.getPassword());
        if (user.getRole() != null) existing.setRole(user.getRole());

        return repository.save(existing);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<User> getAll() {
        return repository.findAll();
    }

    public User getByEmail(String email) {
        return repository.findAll()
                .stream()
                .filter(u -> u.getEmail() != null && u.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);
    }
}
