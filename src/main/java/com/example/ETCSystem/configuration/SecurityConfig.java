package com.example.ETCSystem.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity

public class SecurityConfig {

    // ✅ 0. Cho phép tất cả request (tạm thời khi dev/test)
    @Bean
    @Order(0)
    public SecurityFilterChain openSecurity(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // tắt CSRF để test dễ hơn
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll() // cho phép tất cả
                )
                .formLogin(form -> form.disable()) // tắt form login
                .httpBasic(httpBasic -> httpBasic.disable()); // tắt HTTP Basic Auth
        return http.build();
    }

    // Cấu hình cho ADMIN
//    @Bean
//    @Order(1)
//    public SecurityFilterChain adminSecurity(HttpSecurity http) throws Exception {
//        http
//                .securityMatcher("/admin/**") // chỉ áp dụng cho đường dẫn bắt đầu /admin
//                .csrf(csrf -> csrf.disable())
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/admin/login").permitAll()
//                        .anyRequest().hasRole("ADMIN")
//                )
//                .formLogin(form -> form
//                        .loginPage("/admin/login")
//                        .loginProcessingUrl("/admin/login")
//                        .defaultSuccessUrl("/admin/dashboard", true)
//                        .failureUrl("/admin/login?error=true")
//                        .permitAll()
//                )
//                .logout(logout -> logout
//                        .logoutUrl("/admin/logout")
//                        .logoutSuccessUrl("/admin/login?logout=true")
//                        .permitAll()
//                );
//        return http.build();
//    }
//
//    // Cấu hình cho CUSTOMER
//    @Bean
//    @Order(2)
//    public SecurityFilterChain customerSecurity(HttpSecurity http) throws Exception {
//        http
//                .securityMatcher("/customer/**") // chỉ áp dụng cho /customer/**
//                .csrf(csrf -> csrf.disable())
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/customer/login").permitAll()
//                        .anyRequest().hasRole("CUSTOMER")
//                )
//                .formLogin(form -> form
//                        .loginPage("/customer/login")
//                        .loginProcessingUrl("/customer/login")
//                        .defaultSuccessUrl("/customer/dashboard", true)
//                        .failureUrl("/customer/login?error=true")
//                        .permitAll()
//                )
//                .logout(logout -> logout
//                        .logoutUrl("/customer/logout")
//                        .logoutSuccessUrl("/customer/login?logout=true")
//                        .permitAll()
//                );
//        return http.build();
//    }

    // Dùng password trống hoặc tắt mã hoá tạm thời (chỉ để DEV)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance(); // không mã hóa (chỉ dùng tạm)
    }
}
