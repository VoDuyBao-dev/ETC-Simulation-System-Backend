package com.example.ETCSystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.ETCSystem.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
@Repository
public interface AdminUserRepository extends JpaRepository<User, Long> {
    Page<User> findAll(Pageable pageable);

    @Query("SELECT r FROM User u JOIN u.roles r WHERE u.id = :id")
    String findUserRoleByUserId(@Param("id") Long id);

}
