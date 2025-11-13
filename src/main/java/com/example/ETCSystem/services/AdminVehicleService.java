package com.example.ETCSystem.services;

import com.example.ETCSystem.dto.request.AdminUpdateVehicleRequest;
import com.example.ETCSystem.dto.response.AdminVehicleResponse;
import com.example.ETCSystem.entities.RfidTag;
import com.example.ETCSystem.entities.Vehicle;
import com.example.ETCSystem.enums.TagStatus;
import com.example.ETCSystem.enums.VehicleStatus;
import com.example.ETCSystem.exceptions.AppException;
import com.example.ETCSystem.exceptions.ErrorCode;
import com.example.ETCSystem.mapper.AdminVehicleMapper;
import com.example.ETCSystem.repositories.AdminVehicleRepository;
import com.example.ETCSystem.repositories.RfidTagRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
// import org.springframework.data.domain.PageImpl;
import com.example.ETCSystem.dto.response.PagedResponse;

import java.util.stream.Collectors;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminVehicleService {

    private final AdminVehicleRepository adminVehicleRepository;
    private final AdminVehicleMapper adminVehicleMapper;
    private final RfidTagRepository rfidTagRepository;

    public PagedResponse<AdminVehicleResponse> getAllVehicles(int page, int size) {
        if (page < 0 || size <= 0) {
            throw new AppException(ErrorCode.INVALID_PAGINATION);
        }
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Vehicle> vehiclePage = adminVehicleRepository.findAll(pageRequest);

        List<AdminVehicleResponse> responses = vehiclePage.getContent()
            .stream()
            .map(adminVehicleMapper::toAdminVehicleResponse)
            .toList();

        return PagedResponse.of(
                responses,
                vehiclePage.getNumber(),
                vehiclePage.getSize(),
                vehiclePage.getTotalElements(),
                vehiclePage.getTotalPages());
    }

    public AdminVehicleResponse updateVehicleStatus(Long id, AdminUpdateVehicleRequest request) {
        Vehicle vehicle = adminVehicleRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.VEHICLE_NOT_FOUND));
        RfidTag tag = rfidTagRepository.findByVehicleId(id)
                .orElseThrow(() -> new AppException(ErrorCode.RFID_TAG_NOT_FOUND));
            
        try {
            VehicleStatus newStatus = VehicleStatus.valueOf(request.getStatus().toUpperCase());
            vehicle.setStatus(newStatus);
            if (tag != null) {
                if (newStatus == VehicleStatus.INACTIVE) {
                    tag.setStatus(TagStatus.INACTIVE);
                } else if (newStatus == VehicleStatus.ACTIVE && tag.getStatus() == TagStatus.INACTIVE) {
                    // Nếu xe được kích hoạt lại, cho phép mở tag hoạt động trở lại
                    tag.setStatus(TagStatus.ACTIVE);
                }
                rfidTagRepository.save(tag);
            }
        } catch (IllegalArgumentException e) {
            throw new AppException(ErrorCode.INVALID_STATUS);
        }
        adminVehicleRepository.save(vehicle);

        return adminVehicleMapper.toAdminVehicleResponse(vehicle);
    }
}
