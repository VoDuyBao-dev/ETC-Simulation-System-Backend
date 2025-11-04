package com.example.ETCSystem.services;

import com.example.ETCSystem.dto.request.StationCreateRequest;
import com.example.ETCSystem.dto.request.StationUpdateRequest;
import com.example.ETCSystem.dto.response.StationResponse;
import com.example.ETCSystem.dto.response.StationUpdateStatusResponse;
import com.example.ETCSystem.entities.Station;
import com.example.ETCSystem.exceptions.AppException;
import com.example.ETCSystem.exceptions.ErrorCode;
import com.example.ETCSystem.mapper.StationMapper;
import com.example.ETCSystem.repositories.StationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import com.example.ETCSystem.enums.StationStatus;

@Service
@RequiredArgsConstructor
public class StationService {

    private final StationRepository stationRepository;
    private final StationMapper stationMapper;

    @Transactional(readOnly = true)
    public List<StationResponse> getAllStations() {
        List<Station> stations = stationRepository.findAllByOrderByCreatedAtDesc();
        return stationMapper.toStationResponseList(stations);
    }

    @Transactional(readOnly = true)
    public StationResponse getStationById(Long id) {
        Station st = stationRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.STATION_NOT_FOUND));
        return stationMapper.toStationResponse(st);
    }

    @Transactional
    public StationResponse createStation(StationCreateRequest req) {
        if (stationRepository.existsByCode(req.getCode())) {
            throw new AppException(ErrorCode.STATION_CODE_EXISTS);
        }
        Station entity = stationMapper.toStation(req);
        return stationMapper.toStationResponse(stationRepository.save(entity));
    }

    @Transactional
    public StationResponse updateStation(Long id, StationUpdateRequest req) {
        Station st = stationRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.STATION_NOT_FOUND));
        stationMapper.updateStation(st, req);
        return stationMapper.toStationResponse(stationRepository.save(st));
    }

    @Transactional
    public StationUpdateStatusResponse updateStationStatus(Long id, String newStatus) {
        if (id == null || id <= 0) {
            throw new AppException(ErrorCode.INVALID_KEY);
        }

        Station station = stationRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.STATION_NOT_FOUND));

        // Kiểm tra xem status hợp lệ không
        StationStatus statusEnum;
        try {
            statusEnum = StationStatus.valueOf(newStatus.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new AppException(ErrorCode.INVALID_STATUS);
        }

        // Nếu status không thay đổi thì bỏ qua
        if (station.getStatus() == statusEnum) {
            throw new AppException(ErrorCode.STATION_STATUS_UNCHANGED);
        }

        // Cập nhật và lưu
        station.setStatus(statusEnum);
        stationRepository.save(station);

        return stationMapper.toStationStatusResponse(station);
    }
}
