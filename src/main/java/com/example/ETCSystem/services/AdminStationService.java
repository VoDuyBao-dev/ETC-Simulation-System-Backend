package com.example.ETCSystem.services;

import com.example.ETCSystem.dto.request.AdminCreateStationRequest;
import com.example.ETCSystem.dto.request.AdminUpdateStationRequest;
import com.example.ETCSystem.dto.response.AdminStationResponse;
import com.example.ETCSystem.dto.response.AdminUserResponse;
import com.example.ETCSystem.dto.response.PagedResponse;
import com.example.ETCSystem.entities.Station;
import com.example.ETCSystem.enums.StationStatus;
import com.example.ETCSystem.exceptions.AppException;
import com.example.ETCSystem.exceptions.ErrorCode;
import com.example.ETCSystem.mapper.AdminStationMapper;
import com.example.ETCSystem.repositories.StationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AdminStationService {

    private final StationRepository stationRepository;
    private final AdminStationMapper adminStationMapper;

    private String generateStationCode() {
        long number = (long) (Math.random() * 10000);
        return String.format("ST_%04d", number);
    }

    // Hiển thị toàn bộ danh sách trạm (không phân trang)
    public List<AdminStationResponse> getAllStations() {

        List<Station> stations = stationRepository.findAllByIsDelete(0);

        return stations.stream()
                .map(adminStationMapper::toAdminStationResponse)
                .toList();
    }

    // Thống kê tổng quan trạm thu phí
    public Map<String, Long> getStationStatistics() {

    long total = stationRepository.countByIsDelete(0);
    long active = stationRepository.countByStatusAndIsDelete(StationStatus.ACTIVE, 0);
    long maintenance = stationRepository.countByStatusAndIsDelete(StationStatus.MAINTENANCE, 0);
    long inactive = stationRepository.countByStatusAndIsDelete(StationStatus.INACTIVE, 0);

    Map<String, Long> stats = new HashMap<>();
    stats.put("totalStations", total);
    stats.put("activeStations", active);
    stats.put("maintenanceStations", maintenance);
    stats.put("inactiveStations", inactive);

    return stats;
}

    // Thêm trạm
    public AdminStationResponse createStation(AdminCreateStationRequest req) {
        String code = generateStationCode();
        while (stationRepository.existsByCode(code)) {
            code = generateStationCode();
        }

        if (stationRepository.existsByLatitudeAndLongitude(req.getLatitude(), req.getLongitude())) {
            throw new AppException(ErrorCode.STATION_LOCATION_EXISTS);
        }

        Station station = new Station();
        // station.setCode(req.getCode());
        station.setCode(code);
        station.setName(req.getName());
        station.setAddress(req.getAddress());
        station.setLatitude(req.getLatitude());
        station.setLongitude(req.getLongitude());
        station.setStatus(StationStatus.ACTIVE);

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

    public AdminStationResponse deleteStation(Long id) {

        Station station = stationRepository.findByIdAndIsDelete(id, 0);
        stationRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.VEHICLE_NOT_FOUND));
        if (station == null) {
            throw new AppException(ErrorCode.STATION_NOT_FOUND);
        }

        // Soft delete
        station.setIsDelete(1);
        station.setStatus(StationStatus.INACTIVE);

        Station saved = stationRepository.save(station);

        return adminStationMapper.toAdminStationResponse(saved);
    }
}
