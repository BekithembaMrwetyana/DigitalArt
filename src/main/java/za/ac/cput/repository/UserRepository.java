package za.ac.cput.repository;

import za.ac.cput.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserId(Long userId);
    boolean existsByUserId(Long userId);
    boolean existsByEmail(String email);
    boolean existsByPassword(String password);
    boolean existsByPhoneNumber(String phoneNumber);
    User findByEmail(String email);
}

