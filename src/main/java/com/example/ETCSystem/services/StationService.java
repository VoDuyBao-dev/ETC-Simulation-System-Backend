package com.example.ETCSystem.services;

import com.example.ETCSystem.dto.request.StationCreateRequest;
import com.example.ETCSystem.dto.request.StationUpdateRequest;
import com.example.ETCSystem.dto.response.StationResponse;
import com.example.ETCSystem.entities.Station;
import com.example.ETCSystem.exceptions.AppException;
import com.example.ETCSystem.exceptions.ErrorCode;
import com.example.ETCSystem.mapper.StationMapper;
import com.example.ETCSystem.repositories.RfidReaderRepository;
import com.example.ETCSystem.repositories.StationRepository;
import com.example.ETCSystem.repositories.TollTransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StationService {

    private final StationRepository stationRepository;
    private final RfidReaderRepository rfidReaderRepository;
    private final TollTransactionRepository tollTransactionRepository;
    private final StationMapper stationMapper;

    @Transactional(readOnly = true)
    public List<StationResponse> getAllStations() {
        List<Station> stations = stationRepository.findAllByOrderByCreatedAtDesc();
        return stationMapper.toResponseList(stations);
    }

    @Transactional(readOnly = true)
    public StationResponse getStationById(Long id) {
        Station st = stationRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.STATION_NOT_FOUND));
        return stationMapper.toResponse(st);
    }

    @Transactional
    public StationResponse createStation(StationCreateRequest req) {
        if (stationRepository.existsByCode(req.getCode())) {
            throw new AppException(ErrorCode.STATION_CODE_EXISTS);
        }
        Station entity = stationMapper.toEntity(req);
        return stationMapper.toResponse(stationRepository.save(entity));
    }

    @Transactional
    public StationResponse updateStation(Long id, StationUpdateRequest req) {
        Station st = stationRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.STATION_NOT_FOUND));
        stationMapper.updateEntity(st, req);
        return stationMapper.toResponse(stationRepository.save(st));
    }

    @Transactional
    public void deleteStation(Long id) {
        Station st = stationRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.STATION_NOT_FOUND));

        long readers = rfidReaderRepository.countByStationId(id);
        long txs = tollTransactionRepository.countByStationId(id);
        if (readers > 0 || txs > 0) {
            throw new AppException(
                    ErrorCode.STATION_HAS_DEPENDENCIES);
        }
        stationRepository.delete(st);
    }
}
