package com.example.ETCSystem.services;

import com.example.ETCSystem.dto.response.TransactionHistoryAdminResponse;
import com.example.ETCSystem.dto.response.TransactionHistoryResponse;
import com.example.ETCSystem.entities.RfidReader;
import com.example.ETCSystem.entities.Station;
import com.example.ETCSystem.entities.TollTransaction;
import com.example.ETCSystem.entities.Vehicle;
import com.example.ETCSystem.enums.TollStatus;
import com.example.ETCSystem.enums.TransactionType;
import com.example.ETCSystem.exceptions.AppException;
import com.example.ETCSystem.exceptions.ErrorCode;
import com.example.ETCSystem.projections.TollTransactionProjection;
import com.example.ETCSystem.projections.WalletTransactionProjection;
import com.example.ETCSystem.repositories.TollTransactionRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TollTransactionService {

    TollTransactionRepository tollTransactionRepository;

    public TollTransaction saveTollTransaction(Vehicle vehicle, Station station, RfidReader rfidReader, BigDecimal fee, TollStatus TollStatus, String note) {
        TollTransaction tollTransaction = TollTransaction.builder()
                .vehicle(vehicle)
                .rfidTag(vehicle.getRfidTag())
                .station(station)
                .reader(rfidReader)
                .fee(fee)
                .status(TollStatus)
                .note(note)
                .createdAt(LocalDateTime.now())
                .build();

        try{
            return tollTransactionRepository.save(tollTransaction);
        }catch(Exception e){
            throw new AppException(ErrorCode.SAVE_TOLL_TRANSACTION_FAILED);
        }

    }

//    chức năng lịch sử bên admin
    public Page<TransactionHistoryAdminResponse> getAllHistory(Pageable pageable) {
        Page<TollTransactionProjection> page = tollTransactionRepository.findAllByOrderByCreatedAtDesc(pageable);

        return page.map(this::toDTO);
    }

    private TransactionHistoryAdminResponse toDTO(TollTransactionProjection proj) {
        // 1. Kiểm tra station
        var station = proj.getStation();
        var vehicle = proj.getVehicle();

        if (station == null || station.getName() == null) {
            throw new AppException(ErrorCode.STATION_DATA_MISSING);
        }

        if (vehicle == null || vehicle.getPlateNumber() == null) {
            throw new AppException(ErrorCode.VEHICLE_DATA_MISSING);
        }

        log.info("fee and getCreatedAt: {} - {}", proj.getFee(), proj.getCreatedAt());

        return new TransactionHistoryAdminResponse(
                station.getName(),
                vehicle.getPlateNumber(),
                proj.getFee(),
                proj.getCreatedAt()
        );

    }
}
