package com.example.ETCSystem.projections;

import com.example.ETCSystem.entities.Station;
import com.example.ETCSystem.entities.Vehicle;
import com.example.ETCSystem.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface TollTransactionProjection {
    BigDecimal getFee();
    LocalDateTime getCreatedAt();
    Station getStation();
    Vehicle getVehicle();

    interface Station{
        String getName();
    }
    interface Vehicle{
        String getPlateNumber();
    }

}
