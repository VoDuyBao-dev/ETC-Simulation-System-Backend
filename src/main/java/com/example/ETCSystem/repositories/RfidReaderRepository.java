package com.example.ETCSystem.repositories;

import com.example.ETCSystem.entities.RfidReader;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RfidReaderRepository extends JpaRepository<RfidReader,Long> {
    Optional<RfidReader> findByReaderUid(String readerUid);
}
