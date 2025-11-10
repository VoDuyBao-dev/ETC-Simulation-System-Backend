package com.example.ETCSystem.services;

import com.example.ETCSystem.dto.request.DeviceRequest;
import com.example.ETCSystem.dto.response.DeviceResponse;
import com.example.ETCSystem.entities.*;
import com.example.ETCSystem.enums.StationStatus;
import com.example.ETCSystem.enums.TagStatus;
import com.example.ETCSystem.enums.TollStatus;
import com.example.ETCSystem.enums.TransactionType;
import com.example.ETCSystem.exceptions.AppException;
import com.example.ETCSystem.exceptions.ErrorCode;
import com.example.ETCSystem.repositories.RfidTagRepository;
import com.example.ETCSystem.repositories.TollTransactionRepository;
import com.example.ETCSystem.repositories.WalletRepository;
import com.example.ETCSystem.repositories.WalletTransactionRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class HandlePaymentService {
    RfidTagRepository rfidTagRepository;
    StationService stationService;
    WalletService walletService;
    RfidReaderService rfidReaderService;
    TollFeeService tollFeeService;
    VehicleService vehicleService;
    TollTransactionService tollTransactionService;
    WalletRepository walletRepository;
    WalletTransactionService walletTransactionService;
    TagReadService tagReadService;

    public DeviceResponse handlePayment(DeviceRequest deviceRequest) {

//        Lưu tag mà rfid reader đọc được
        TagRead tagRead = tagReadService.saveTagRead(deviceRequest);

        Wallet wallet = walletService.getWalletByRfidTag(deviceRequest);
        Station station = stationService.getStationByCode(deviceRequest.getStationCode());

        if(station.getStatus() != StationStatus.ACTIVE){
            throw new AppException(ErrorCode.STATION_NOT_ACTIVE);
        }

        RfidReader rfidReader = rfidReaderService.getRfidReader(deviceRequest.getReaderUid());
        if(!rfidReader.getStation().getId().equals(station.getId())){
            throw new AppException(ErrorCode.RFID_READER_NOT_BELONG_TO_STATION);
        } else if (rfidReader.getIsActive() != true ) {
            throw new AppException(ErrorCode.RFID_READER_NOT_ACTIVE);

        }

        Vehicle vehicle = vehicleService.getVehicleByRfidTag(deviceRequest.getRfidTagCode());

        BigDecimal fee = tollFeeService.getFeeByVehicle(vehicle);

        String note;
        boolean success = true;

        if(wallet.getBalance().compareTo(fee) < 0){
            success = false;
            note = "Thu phí qua làn thất bại!";
//            luưu TollTransacton với trạng thái FAILED_BALANCE
            tollTransactionService.saveTollTransaction(vehicle, station, rfidReader, fee, TollStatus.FAILED_BALANCE, note);

//          Cập nhật TagRead process result = FAILED
            tagReadService.updateTagReadProcessResult(tagRead, success);

            throw new AppException(ErrorCode.WALLET_BALANCE_NOT_ENOUGH);

        }

//        trừ tiền trong ví
        BigDecimal balanceAfter = wallet.getBalance().subtract(fee);
        wallet.setBalance(balanceAfter);
        walletRepository.save(wallet);

//            lưu TollTransaction với trạng thái SUCCESS
        note = "Thu phí qua làn thành công!";
        TollTransaction tollTransaction = tollTransactionService.saveTollTransaction(vehicle, station, rfidReader, fee, TollStatus.SUCCESS, note);

//        lưu Wallet Transaction
        String description = "Trừ phí qua trạm "+ station.getName();
        walletTransactionService.saveWalletTransaction(wallet, tollTransaction, fee, TransactionType.DEDUCT, description, balanceAfter);

//        Cập nhật tag read process result = SUCCESS
        tagReadService.updateTagReadProcessResult(tagRead, success);

        return DeviceResponse.builder()
                .messsage("Thanh toán thành công")
                .amount(fee.negate())
                .vehiclePlate(vehicle.getPlateNumber())
                .build();


    }







}
