package za.ac.cput.repository;

import za.ac.cput.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserId(Long userId);
    boolean existsByUserId(Long userId);
    boolean existsByEmail(String email);
    boolean existsByPassword(String password);
    boolean existsByPhoneNumber(String phoneNumber);

    Optional<User> findByEmail(String email);
}


