package com.example.ETCSystem.services;

import com.example.ETCSystem.dto.request.AdminUpdateVehicleRequest;
import com.example.ETCSystem.dto.response.AdminVehicleResponse;
import com.example.ETCSystem.entities.Vehicle;
import com.example.ETCSystem.enums.AccountStatus;
import com.example.ETCSystem.enums.VehicleStatus;
import com.example.ETCSystem.exceptions.AppException;
import com.example.ETCSystem.exceptions.ErrorCode;
import com.example.ETCSystem.mapper.AdminVehicleMapper;
import com.example.ETCSystem.repositories.AdminVehicleRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.stream.Collectors;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminVehicleService {

    private final AdminVehicleRepository adminVehicleRepository;
    private final AdminVehicleMapper adminVehicleMapper;

    public Page<AdminVehicleResponse> getAllVehicles(int page, int size) {
        if (page < 0 || size <= 0) {
            throw new AppException(ErrorCode.INVALID_PAGINATION);
        }
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Vehicle> vehiclePage = adminVehicleRepository.findAll(pageRequest);

        List<AdminVehicleResponse> responses = vehiclePage.getContent().stream()
                .map(vehicle -> {
                    return adminVehicleMapper.toAdminVehicleResponse(vehicle);
                })
                .collect(Collectors.toList());

        return new PageImpl<>(responses, pageRequest, vehiclePage.getTotalElements());
    }


    public AdminVehicleResponse updateVehicleStatus(Long id, AdminUpdateVehicleRequest request) {
        Vehicle vehicle = adminVehicleRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.VEHICLE_NOT_FOUND));

        try {
            VehicleStatus newStatus = VehicleStatus.valueOf(request.getStatus().toUpperCase());
            vehicle.setStatus(newStatus);
        } catch (IllegalArgumentException e) {
            throw new AppException(ErrorCode.INVALID_STATUS); // tự định nghĩa thêm trong ErrorCode
        }
        adminVehicleRepository.save(vehicle);

        return adminVehicleMapper.toAdminVehicleResponse(vehicle);
    }
}
