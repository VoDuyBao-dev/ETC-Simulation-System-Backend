package com.example.ETCSystem.configuration;

import com.example.ETCSystem.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

import static org.springframework.security.oauth2.jwt.JwtTypeValidator.jwt;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final String[] PUBLIC_URLS = {
            "/auth/register",
            "/auth/login",
            "/auth/logout",
            "/auth/introspect",
            "/auth/otp/verify",
            "/auth/otp/resend",

    };

    private final String[] PRIVATE_URLS = {
            "/admin/users",

    };

    @Autowired
    private CustomJwtDecoder customJwtDecoder;

    @Bean
    public SecurityFilterChain openSecurity(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, PUBLIC_URLS).permitAll()
                        .anyRequest().authenticated()
                )

                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.decoder(customJwtDecoder))
                        .authenticationEntryPoint(new JwtAuthenticationEntryPoint())
                )
                .csrf(csrf -> csrf.disable());

        return httpSecurity.build();
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

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }
}
