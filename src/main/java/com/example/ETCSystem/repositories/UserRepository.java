package com.example.ETCSystem.repositories;

import com.example.ETCSystem.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);
    List<User> findAllByOrderByCreatedAtDesc();
    boolean existsByEmail(String email);
}
