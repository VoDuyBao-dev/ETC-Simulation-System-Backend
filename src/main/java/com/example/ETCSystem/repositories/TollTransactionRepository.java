package com.example.ETCSystem.repositories;

import com.example.ETCSystem.entities.TollTransaction;
import com.example.ETCSystem.projections.TollTransactionProjection;
import com.example.ETCSystem.enums.TollStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

import java.util.List;
@Repository
public interface TollTransactionRepository extends JpaRepository<TollTransaction, Long> {
    List<TollTransactionProjection> findAllByOrderByCreatedAtDesc();
  long countByStatus(TollStatus status);

  long countByStationId(Long stationId);

  @Query("""
          select year(t.createdAt), month(t.createdAt), coalesce(sum(t.fee), 0)
          from TollTransaction t
          where t.status = com.example.ETCSystem.enums.TollStatus.SUCCESS
            and t.createdAt between :from and :to
          group by year(t.createdAt), month(t.createdAt)
          order by year(t.createdAt), month(t.createdAt)
      """)
  List<Object[]> sumRevenueByMonth(@Param("from") LocalDateTime from, @Param("to") LocalDateTime to);

  @Query("""
          select s.id, s.code, s.name, coalesce(sum(t.fee), 0)
          from TollTransaction t join t.station s
          where t.status = com.example.ETCSystem.enums.TollStatus.SUCCESS
            and t.createdAt between :from and :to
          group by s.id, s.code, s.name
          order by coalesce(sum(t.fee), 0) desc
      """)
  List<Object[]> topStationsRevenue(@Param("from") LocalDateTime from,
      @Param("to") LocalDateTime to,
      Pageable pageable);

  @Query("""
          select t.id, s.name, v.plateNumber, t.createdAt, cast(t.status as string), t.note
          from TollTransaction t
          join t.vehicle v
          join t.station s
          where t.status in (com.example.ETCSystem.enums.TollStatus.ERROR, com.example.ETCSystem.enums.TollStatus.FAILED_BALANCE)
            and (:stationName is null or s.name = :stationName)
            and t.createdAt between :from and :to
          order by t.createdAt desc
      """)
  Page<Object[]> findFailedRows(
      @Param("stationName") String stationName,
      @Param("from") LocalDateTime from,
      @Param("to") LocalDateTime to,
      Pageable pageable);

  @Query("""
          select t.id, s.name, v.plateNumber, t.createdAt, cast(t.status as string), t.note
          from TollTransaction t
          join t.vehicle v
          join t.station s
          where t.status in (com.example.ETCSystem.enums.TollStatus.ERROR, com.example.ETCSystem.enums.TollStatus.FAILED_BALANCE)
            and (:stationName is null or s.name = :stationName)
            and t.createdAt between :from and :to
          order by t.createdAt desc
      """)
  List<Object[]> findFailedRows(
      @Param("stationName") String stationName,
      @Param("from") LocalDateTime from,
      @Param("to") LocalDateTime to);

}
