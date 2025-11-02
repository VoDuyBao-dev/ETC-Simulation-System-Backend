package com.example.ETCSystem.configuration;

import com.example.ETCSystem.entities.User;
import com.example.ETCSystem.enums.AccountStatus;
import com.example.ETCSystem.enums.Role;
import com.example.ETCSystem.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.HashSet;

@Configuration
@Slf4j
public class ApplicationInitConfig {
    @Autowired
    PasswordEncoder passwordEncoder;

    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository) {
        return args -> {
            if(userRepository.findByUsername("admin").isEmpty()) {
                 var roles = new HashSet<String>();
                 roles.add(Role.ADMIN.name());
                User user = User.builder()
                        .username("admin")
                        .roles(roles)
                        .password(passwordEncoder.encode("admin"))
                        .enabled(true)
                        .status(AccountStatus.ACTIVE)
                        .createdAt(LocalDateTime.now())
                        .build();
                userRepository.save(user);
                log.info("created default admin user with username: admin and password: admin");
            }
        };
    }
}
