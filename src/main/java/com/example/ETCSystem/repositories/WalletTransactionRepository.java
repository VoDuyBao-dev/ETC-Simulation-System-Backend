package com.example.ETCSystem.repositories;

import com.example.ETCSystem.entities.WalletTransaction;
import com.example.ETCSystem.projections.WalletTransactionProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WalletTransactionRepository extends JpaRepository<WalletTransaction, Long> {
    @Query("""
        FROM WalletTransaction wt
                                      WHERE wt.wallet.user.userId = :userId
                                      ORDER BY wt.createdAt DESC
""")
    Page<WalletTransactionProjection> findTransactionHistoryByUserId(@Param("userId") Long userId, Pageable pageable);
}
