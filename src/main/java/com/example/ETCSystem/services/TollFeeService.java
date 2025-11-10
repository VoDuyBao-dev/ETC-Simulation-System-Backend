package com.example.ETCSystem.services;

import com.example.ETCSystem.entities.Vehicle;
import com.example.ETCSystem.exceptions.AppException;
import com.example.ETCSystem.exceptions.ErrorCode;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class TollFeeService {
    public BigDecimal getFeeByVehicle(Vehicle vehicle) {
        switch (vehicle.getVehicleType()) {
            case CAR:
                return new BigDecimal("30000");
            case TRUCK:
                return new BigDecimal("50000");
            default:
                throw new AppException(ErrorCode.VEHICLE_TYPE_UNVALID);
        }
    }

}
