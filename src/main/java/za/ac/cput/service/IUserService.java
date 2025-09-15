package za.ac.cput.service;

import za.ac.cput.domain.User;
import java.util.List;

public interface IUserService extends IService<User, Long> {
    List<User> getAll();
    User partialUpdate(User user);
}
