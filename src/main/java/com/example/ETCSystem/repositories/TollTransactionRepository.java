package com.example.ETCSystem.repositories;

import com.example.ETCSystem.entities.TollTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TollTransactionRepository extends JpaRepository<TollTransaction, Long> {
    long countByStationId(Long stationId);
}
