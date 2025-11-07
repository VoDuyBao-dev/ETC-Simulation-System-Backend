package com.example.ETCSystem.repositories;

import com.example.ETCSystem.entities.RfidTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RfidTagRepository extends JpaRepository<RfidTag, Long> {
}
