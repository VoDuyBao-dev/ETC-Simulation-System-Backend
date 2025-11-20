package com.example.ETCSystem.services;

import com.example.ETCSystem.dto.request.DeviceRequest;
import com.example.ETCSystem.entities.RfidTag;
import com.example.ETCSystem.entities.Vehicle;
import com.example.ETCSystem.entities.Wallet;
import com.example.ETCSystem.enums.TagStatus;
import com.example.ETCSystem.exceptions.AppException;
import com.example.ETCSystem.exceptions.ErrorCode;
import com.example.ETCSystem.repositories.RfidTagRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class VehicleService {

    RfidTagRepository rfidTagRepository;

    public Vehicle getVehicleByRfidTag(String getRfidTagCode) {
        RfidTag rfidTag = rfidTagRepository.findByTagUid(getRfidTagCode)
                .orElseThrow(() -> new AppException(ErrorCode.RFID_TAG_NOT_EXISTED));

        if (rfidTag.getStatus() != TagStatus.ACTIVE) {
            throw new AppException(ErrorCode.RFID_TAG_NOT_ACTIVE);
        }

        Vehicle vehical = rfidTag.getVehicle();
        if (vehical == null) {
            throw new AppException(ErrorCode.VEHICLE_NOT_EXISTED);
        }

        return  vehical;
    }
//    đến đoạn trả về cái xe để xem loại và tính tiền rồi
}
