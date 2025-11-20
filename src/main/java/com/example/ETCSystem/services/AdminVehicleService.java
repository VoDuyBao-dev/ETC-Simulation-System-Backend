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

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminVehicleService {

    private final AdminVehicleRepository adminVehicleRepository;
    private final AdminVehicleMapper adminVehicleMapper;
    private final RfidTagRepository rfidTagRepository;

    public List<AdminVehicleResponse> getAllVehicles() {
        List<Vehicle> vehicle = adminVehicleRepository.findAllByOrderByCreatedAtDesc();

        return vehicle.stream()
                .map(adminVehicleMapper::toAdminVehicleResponse)
                .toList();
    }

    public AdminVehicleResponse updateVehicleStatus(Long id, AdminUpdateVehicleRequest request) {

        Vehicle vehicle = adminVehicleRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.VEHICLE_NOT_FOUND));

        List<RfidTag> tags = rfidTagRepository.findAllByVehicleId(id);

        RfidTag activeTag = tags.stream()
                .filter(t -> t.getStatus() == TagStatus.ACTIVE)
                .findFirst()
                .orElse(null);

        VehicleStatus newStatus;
        try {
            newStatus = VehicleStatus.valueOf(request.getStatus().trim().toUpperCase());
        } catch (Exception e) {
            throw new AppException(ErrorCode.INVALID_STATUS);
        }

        vehicle.setStatus(newStatus);

        if (activeTag != null) {
            if (newStatus == VehicleStatus.INACTIVE) {
                activeTag.setStatus(TagStatus.INACTIVE);
            } else if (newStatus == VehicleStatus.ACTIVE && activeTag.getStatus() == TagStatus.INACTIVE) {
                activeTag.setStatus(TagStatus.ACTIVE);
            }
            rfidTagRepository.save(activeTag);
        }

        adminVehicleRepository.save(vehicle);
        return adminVehicleMapper.toAdminVehicleResponse(vehicle);
    }

}
