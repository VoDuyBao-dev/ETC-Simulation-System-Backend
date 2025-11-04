package com.example.ETCSystem.services;

import com.example.ETCSystem.dto.request.VehicleCreateRequest;
import com.example.ETCSystem.dto.request.VehicleUpdateRequest;
import com.example.ETCSystem.dto.response.VehicleResponse;
import com.example.ETCSystem.entities.User;
import com.example.ETCSystem.entities.Vehicle;
import com.example.ETCSystem.exceptions.AppException;
import com.example.ETCSystem.exceptions.ErrorCode;
import com.example.ETCSystem.mapper.VehicleMapper;
import com.example.ETCSystem.repositories.UserRepository;
import com.example.ETCSystem.repositories.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VehicleService {
    private final VehicleRepository vehicleRepository;
    private final UserRepository userRepository;
    private final VehicleMapper vehicleMapper;

    public List<VehicleResponse> getAllVehicles() {
        return vehicleMapper.toVehicleResponseList(vehicleRepository.findAll());
    }

    public VehicleResponse getVehicleById(Long id) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.VEHICLE_NOT_FOUND));
        return vehicleMapper.toVehicleResponse(vehicle);
    }

    public VehicleResponse createVehicle(VehicleCreateRequest request) {
        if (vehicleRepository.existsByPlateNumber(request.getPlateNumber())) {
            throw new AppException(ErrorCode.VEHICLE_ALREADY_EXISTS);
        }

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        Vehicle vehicle = vehicleMapper.toVehicle(request);
        vehicle.setUser(user);
        Vehicle saved = vehicleRepository.save(vehicle);
        return vehicleMapper.toVehicleResponse(saved);
    }

    public VehicleResponse updateVehicle(Long id, VehicleUpdateRequest request) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.VEHICLE_NOT_FOUND));

        vehicle.setBrand(request.getBrand());
        vehicle.setModel(request.getModel());
        vehicle.setColor(request.getColor());
        vehicle.setVehicleType(request.getVehicleType());

        Vehicle updated = vehicleRepository.save(vehicle);
        return vehicleMapper.toVehicleResponse(updated);
    }

}
