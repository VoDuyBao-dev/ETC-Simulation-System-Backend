package com.example.ETCSystem.repositories;

import com.example.ETCSystem.entities.RfidReader;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

import org.springframework.stereotype.Repository;
@Repository
public interface RfidReaderRepository extends JpaRepository<RfidReader, Long> {
    boolean existsByReaderUid(String readerUid);
    Optional<RfidReader> findByReaderUid(String readerUid);}

