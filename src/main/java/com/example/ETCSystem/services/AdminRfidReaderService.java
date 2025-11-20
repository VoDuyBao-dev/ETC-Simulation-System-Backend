package com.example.ETCSystem.services;

import com.example.ETCSystem.dto.request.CreateRfidReaderRequest;
import com.example.ETCSystem.dto.request.UpdateRfidReaderInfoRequest;
import com.example.ETCSystem.dto.request.UpdateRfidReaderStatusRequest;
import com.example.ETCSystem.dto.response.AdminRfidReaderResponse;
import com.example.ETCSystem.entities.RfidReader;
import com.example.ETCSystem.entities.Station;
import com.example.ETCSystem.exceptions.AppException;
import com.example.ETCSystem.exceptions.ErrorCode;
import com.example.ETCSystem.mapper.AdminRfidReaderMapper;
import com.example.ETCSystem.repositories.RfidReaderRepository;
import com.example.ETCSystem.repositories.StationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminRfidReaderService {

    private final RfidReaderRepository rfidReaderRepository;
    private final StationRepository stationRepository;
    private final AdminRfidReaderMapper adminRfidReaderMapper;

    // Xem danh sách
    public List<AdminRfidReaderResponse> getAllReaders() {
        return rfidReaderRepository.findAll()
                .stream()
                .map(adminRfidReaderMapper::toAdminRfidReaderResponse)
                .collect(Collectors.toList());
    }

    // Thêm mới
    public AdminRfidReaderResponse createReader(CreateRfidReaderRequest req) {
        if (rfidReaderRepository.existsByReaderUid(req.getReaderUid())) {
            throw new AppException(ErrorCode.READER_UID_EXISTS);
        }

        Station station = stationRepository.findById(req.getStationId())
                .orElseThrow(() -> new AppException(ErrorCode.STATION_NOT_FOUND));

        RfidReader reader = new RfidReader();
        reader.setReaderUid(req.getReaderUid());
        reader.setDescription(req.getDescription());
        reader.setStation(station);
        reader.setIsActive(true);
        reader.setCreatedAt(LocalDateTime.now());

        rfidReaderRepository.save(reader);
        return adminRfidReaderMapper.toAdminRfidReaderResponse(reader);
    }

    // Sửa trạng thái (Active/Inactive)
    public AdminRfidReaderResponse updateReaderStatus(Long id, UpdateRfidReaderStatusRequest req) {
        RfidReader reader = rfidReaderRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.READER_NOT_FOUND));

        reader.setIsActive(req.getIsActive());
        reader.setLastHeartbeat(LocalDateTime.now());
        rfidReaderRepository.save(reader);

        return adminRfidReaderMapper.toAdminRfidReaderResponse(reader);
    }

    // Sửa thông tin (mô tả, trạm)
    public AdminRfidReaderResponse updateReaderInfo(Long id, UpdateRfidReaderInfoRequest req) {
        RfidReader reader = rfidReaderRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.READER_NOT_FOUND));

        reader.setDescription(req.getDescription());

        if (req.getStationId() != null) {
            Station station = stationRepository.findById(req.getStationId())
                    .orElseThrow(() -> new AppException(ErrorCode.STATION_NOT_FOUND));
            reader.setStation(station);
        }

        rfidReaderRepository.save(reader);
        return adminRfidReaderMapper.toAdminRfidReaderResponse(reader);
    }
}
