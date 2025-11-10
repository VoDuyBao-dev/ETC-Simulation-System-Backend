package com.example.ETCSystem.repositories;


import com.example.ETCSystem.entities.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
     Optional<Wallet> findByUser_UserId(Long userId);
}
