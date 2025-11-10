package com.example.ETCSystem.services;

import com.example.ETCSystem.entities.Station;
import com.example.ETCSystem.exceptions.AppException;
import com.example.ETCSystem.exceptions.ErrorCode;
import com.example.ETCSystem.repositories.StationRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StationService {
    StationRepository stationRepository;

    public Station getStationByCode(String stationCode) {
        return stationRepository.findByCode(stationCode).orElseThrow(() -> new AppException(ErrorCode.STATION_NOT_EXISTED));
    }
}
