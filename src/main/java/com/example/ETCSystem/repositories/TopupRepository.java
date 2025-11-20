package com.example.ETCSystem.repositories;

import com.example.ETCSystem.entities.Topup;
import com.example.ETCSystem.enums.TopupStatus;
import com.example.ETCSystem.projections.TopupHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TopupRepository extends JpaRepository<Topup,Long> {
    Optional<Topup> findByReferenceCode(String referenceCode);

    List<TopupHistory> findByUser_UsernameAndStatusOrderByCreatedAtDesc(String username, TopupStatus status);



}
