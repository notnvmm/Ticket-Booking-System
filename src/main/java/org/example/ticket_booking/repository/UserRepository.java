package org.example.ticket_booking.repository;

import org.example.ticket_booking.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    //SELECT * from users WHERE username=?
    Optional<User> findByUsername(String username);

    //SELECT * FROM users WHERE email=?
    Optional<User> findByEmail(String email);

    //SELECT COUNT(*) FROM users WHERE email=? (returns true/false)
    boolean existsByEmail(String email);
}