package com.example.ETCSystem.services;

import com.example.ETCSystem.dto.request.AdminCreateStationRequest;
import com.example.ETCSystem.dto.request.AdminUpdateStationRequest;
import com.example.ETCSystem.dto.response.AdminStationResponse;
import com.example.ETCSystem.entities.Station;
import com.example.ETCSystem.enums.StationStatus;
import com.example.ETCSystem.exceptions.AppException;
import com.example.ETCSystem.exceptions.ErrorCode;
import com.example.ETCSystem.mapper.AdminStationMapper;
import com.example.ETCSystem.repositories.StationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminStationService {

    private final StationRepository stationRepository;
    private final AdminStationMapper adminStationMapper;

    // Hiển thị danh sách trạm
    public Page<AdminStationResponse> getAllStations(int page, int size) {
        if (page < 0 || size <= 0) {
            throw new AppException(ErrorCode.INVALID_PAGINATION);
        }
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Station> stationPage = stationRepository.findAll(pageRequest);

        List<AdminStationResponse> responses = stationPage.getContent().stream()
                .map(station -> {
                    AdminStationResponse res = adminStationMapper.toAdminStationResponse(station);
                    return res;
                })
                .collect(Collectors.toList());

        return new PageImpl<>(responses, pageRequest, stationPage.getTotalElements());
    }

    // Thêm trạm
    public AdminStationResponse createStation(AdminCreateStationRequest req) {
        if (stationRepository.existsByCode(req.getCode())) {
            throw new AppException(ErrorCode.STATION_CODE_EXISTS);
        }

        Station station = new Station();
        station.setCode(req.getCode());
        station.setName(req.getName());
        station.setAddress(req.getAddress());
        station.setLatitude(req.getLatitude());
        station.setLongitude(req.getLongitude());
        station.setStatus(req.getStatus());


        Station saved = stationRepository.save(station);
        AdminStationResponse res = adminStationMapper.toAdminStationResponse(saved);
        return res;
    }

    // Sửa thông tin 
    public AdminStationResponse updateStation(Long id, AdminUpdateStationRequest req) {
        Station station = stationRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.STATION_NOT_FOUND));

        if (req.getName() != null)
            station.setName(req.getName());
        if (req.getAddress() != null)
            station.setAddress(req.getAddress());
        if (req.getLatitude() != null)
            station.setLatitude(req.getLatitude());
        if (req.getLongitude() != null)
            station.setLongitude(req.getLongitude());
        Station updated = stationRepository.save(station);

        return adminStationMapper.toAdminStationResponse(updated);
    }
    // Sửa trạng thái
        public AdminStationResponse updateStationStatus(Long id, AdminUpdateStationRequest request) {
        Station station = stationRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.VEHICLE_NOT_FOUND));

        try {
            StationStatus newStatus = StationStatus.valueOf(request.getStatus().toUpperCase());
            station.setStatus(newStatus);
        } catch (IllegalArgumentException e) {
            throw new AppException(ErrorCode.INVALID_STATUS);
        }
        Station update = stationRepository.save(station);

        return adminStationMapper.toAdminStationResponse(update);
    }
}

