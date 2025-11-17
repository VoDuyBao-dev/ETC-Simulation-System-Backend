package com.example.ETCSystem.services;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import java.util.UUID;

import com.example.ETCSystem.dto.request.RegisterVehicleRequest;
import com.example.ETCSystem.dto.request.UpdateVehicleStatusRequest;
import com.example.ETCSystem.dto.response.PagedResponse;
import com.example.ETCSystem.dto.response.VehicleResponse;
import com.example.ETCSystem.entities.RfidTag;
import com.example.ETCSystem.entities.User;
import com.example.ETCSystem.entities.Vehicle;
import com.example.ETCSystem.enums.TagStatus;
import com.example.ETCSystem.enums.VehicleStatus;
import com.example.ETCSystem.exceptions.AppException;
import com.example.ETCSystem.exceptions.ErrorCode;
import com.example.ETCSystem.repositories.UserRepository;
import com.example.ETCSystem.repositories.VehicleRepository;
import com.example.ETCSystem.repositories.RfidTagRepository;
import com.example.ETCSystem.mapper.VehicleMapper;
import com.example.ETCSystem.services.UserService;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class VehicleService {
    private final VehicleRepository vehicleRepository;
    private final UserRepository userRepository;
    private final RfidTagRepository rfidTagRepository;
    private final VehicleMapper vehicleMapper;
    private final UserService userService;

    public Vehicle getVehicleByRfidTag(String getRfidTagCode) {
        RfidTag rfidTag = rfidTagRepository.findByTagUid(getRfidTagCode)
                .orElseThrow(() -> new AppException(ErrorCode.RDIF_TAG_NOT_EXISTED));

        if (rfidTag.getStatus() != TagStatus.ACTIVE) {
            throw new AppException(ErrorCode.RDIF_TAG_NOT_ACTIVE);
        }

        Vehicle vehical = rfidTag.getVehicle();
        if (vehical == null) {
            throw new AppException(ErrorCode.VEHICLE_NOT_EXISTED);
        }

        return vehical;
    }

    // đăng kí xe mới
    public VehicleResponse registerVehicle(RegisterVehicleRequest request) {
        User currentUser = userService.getCurrentUser();
        Long userId = currentUser.getUserId();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        Vehicle vehicle = new Vehicle();
        vehicle.setUser(user);
        vehicle.setPlateNumber(request.getPlateNumber());
        vehicle.setVehicleType(request.getVehicleType());
        // vehicle.setBrand(request.getBrand());
        // vehicle.setModel(request.getModel());
        // vehicle.setColor(request.getColor());
        vehicle.setStatus(VehicleStatus.ACTIVE);
        vehicleRepository.save(vehicle);

        RfidTag tag = new RfidTag();
        tag.setVehicle(vehicle);
        tag.setTagUid(UUID.randomUUID().toString().substring(0, 5));
        tag.setStatus(TagStatus.ACTIVE);
        rfidTagRepository.save(tag);
        vehicle.setRfidTag(tag);
        return vehicleMapper.toVehicleResponse(vehicle);
    }

    // đổi trạng thái của thẻ xe
    public VehicleResponse updateRfidTagStatus(Long vehicleId) {

        User currentUser = userService.getCurrentUser();
        Long userId = currentUser.getUserId();

        userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new AppException(ErrorCode.VEHICLE_NOT_FOUND));

        if (!vehicle.getUser().getUserId().equals(currentUser.getUserId())) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }
        RfidTag tag = rfidTagRepository.findByVehicleId(vehicleId)
                .orElseThrow(() -> new AppException(ErrorCode.RFID_TAG_NOT_FOUND));

        tag.setStatus(TagStatus.INACTIVE);
        rfidTagRepository.save(tag);

        return vehicleMapper.toVehicleResponse(vehicle);
    }

    // //
    // public void reissueTag(Long vehicleId) {
    // RfidTag oldTag = rfidTagRepository.findByVehicleId(vehicleId)
    // .orElseThrow(() -> new AppException(ErrorCode.RFID_TAG_NOT_FOUND));
    // oldTag.setStatus(TagStatus.INACTIVE);
    // rfidTagRepository.save(oldTag);

    // RfidTag newTag = new RfidTag();
    // newTag.setVehicle(oldTag.getVehicle());
    // newTag.setTagUid(UUID.randomUUID().toString().substring(0, 5));
    // newTag.setStatus(TagStatus.ACTIVE);
    // rfidTagRepository.save(newTag);
    // }

    public VehicleResponse updateVehicleStatus(Long vehicleId, UpdateVehicleStatusRequest request) {
        User currentUser = userService.getCurrentUser();
        Long userId = currentUser.getUserId();

        System.out.println("Current User ID: "
                + currentUser.getUserId()
                + " | Type: "
                + currentUser.getUserId().getClass().getName());

        userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new AppException(ErrorCode.VEHICLE_NOT_FOUND));
                
// Nếu không phải người dùng đang đăng nhập sửa trạng thái thì sẽ báo lỗi 
        if (!vehicle.getUser().getUserId().equals(currentUser.getUserId())) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }

        RfidTag tag = rfidTagRepository.findByVehicleId(vehicleId)
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

        vehicleRepository.save(vehicle);
        rfidTagRepository.save(tag);
        return vehicleMapper.toVehicleResponse(vehicle);
    }

    // danh sách xe của người dùng đã đăng nhập
    public PagedResponse<VehicleResponse> getUserVehicles(int page, int size) {
        if (page < 0 || size <= 0) {
            throw new AppException(ErrorCode.INVALID_PAGINATION);
        }
        User currentUser = userService.getCurrentUser();
        Long userId = currentUser.getUserId();

        PageRequest pageable = PageRequest.of(page, size);
        Page<Vehicle> vehicles = vehicleRepository.findByUserUserId(userId, pageable);
        List<VehicleResponse> responses = vehicles.getContent()
                .stream()
                .map(vehicleMapper::toVehicleResponse)
                .toList();

        return PagedResponse.of(
                responses,
                vehicles.getNumber(),
                vehicles.getSize(),
                vehicles.getTotalElements(),
                vehicles.getTotalPages());
    }

}
