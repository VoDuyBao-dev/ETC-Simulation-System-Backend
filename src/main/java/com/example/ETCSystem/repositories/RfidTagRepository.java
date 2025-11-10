package com.example.ETCSystem.repositories;

import com.example.ETCSystem.entities.RfidTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RfidTagRepository extends JpaRepository<RfidTag,Long> {
    Optional<RfidTag> findByTagUid(String tagUid);
}
