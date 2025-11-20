package com.example.ETCSystem.repositories;

import com.example.ETCSystem.entities.TollTransaction;
import com.example.ETCSystem.projections.TollTransactionProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TollTransactionRepository extends JpaRepository<TollTransaction,Long> {

    List<TollTransactionProjection> findAllByOrderByCreatedAtDesc();

}
