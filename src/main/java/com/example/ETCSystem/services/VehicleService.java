package com.example.ETCSystem.services;

import java.time.LocalDateTime;
import java.util.List;

// import org.springframework.data.domain.PageRequest;
// import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import java.util.UUID;

import com.example.ETCSystem.dto.request.RegisterVehicleRequest;
import com.example.ETCSystem.dto.request.UpdateVehicleStatusRequest;
// import com.example.ETCSystem.dto.response.PagedResponse;
import com.example.ETCSystem.dto.response.VehicleResponse;
import com.example.ETCSystem.dto.response.RfidTagResponse;
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
// import com.example.ETCSystem.services.UserService;
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
                .orElseThrow(() -> new AppException(ErrorCode.RFID_TAG_NOT_EXISTED));

        if (rfidTag.getStatus() != TagStatus.ACTIVE) {
            throw new AppException(ErrorCode.RFID_TAG_NOT_ACTIVE);
        }

        Vehicle vehical = rfidTag.getVehicle();
        if (vehical == null) {
            throw new AppException(ErrorCode.VEHICLE_NOT_EXISTED);
        }

        return vehical;
    }

    // Đăng ký xe mới
    public VehicleResponse registerVehicle(RegisterVehicleRequest request) {

        User currentUser = userService.getCurrentUser();
        Long userId = currentUser.getUserId();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        // 1. Tạo xe mới
        Vehicle vehicle = new Vehicle();
        vehicle.setUser(user);
        vehicle.setPlateNumber(request.getPlateNumber());
        vehicle.setVehicleType(request.getVehicleType());
        vehicle.setStatus(VehicleStatus.ACTIVE);

        vehicleRepository.save(vehicle);

        // 2. Tạo thẻ E-Tag đầu tiên cho xe
        RfidTag tag = new RfidTag();
        tag.setVehicle(vehicle);
        tag.setTagUid(generateTagUid()); // Hàm sinh mã thẻ chuẩn hơn
        tag.setStatus(TagStatus.ACTIVE);
        tag.setIssuedAt(LocalDateTime.now());

        rfidTagRepository.save(tag);
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

    // Đổi trạng thái của xe và thẻ xe sẽ đổi theo
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
    public List<VehicleResponse> getUserVehicles() {

        User currentUser = userService.getCurrentUser();
        Long userId = currentUser.getUserId();

        List<Vehicle> vehicles = vehicleRepository.findByUserUserId(userId);

        return vehicles.stream()
                .map(vehicleMapper::toVehicleResponse)
                .toList();
    }

    // @Transactional
    public RfidTagResponse reportLostAndIssueNewTag(Long vehicleId) {

        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new AppException(ErrorCode.VEHICLE_NOT_FOUND));

        // 1. Lấy thẻ ACTIVE hiện tại
        RfidTag oldTag = rfidTagRepository
                .findByVehicleIdAndStatus(vehicleId, TagStatus.ACTIVE)
                .orElseThrow(() -> new AppException(ErrorCode.RFID_TAG_NOT_FOUND));

        // 2. Vô hiệu hóa thẻ cũ
        oldTag.setStatus(TagStatus.INACTIVE);
        oldTag.setLastSuccessfulPassage(null);
        oldTag.setLastPassageStationId(null);
        rfidTagRepository.save(oldTag);

        // 3. Tạo thẻ mới (chưa gán UID)
        RfidTag newTag = RfidTag.builder()
                .vehicle(vehicle)
                .status(TagStatus.ACTIVE)
                .issuedAt(LocalDateTime.now())
                .build();

        // 4. Lưu trước để lấy id tự tăng
        rfidTagRepository.save(newTag);

        // String generatedUid = "TAG" + String.format("%05d", newTag.getId());
        // newTag.setTagUid(generatedUid);
        newTag.setTagUid(generateTagUid());

        rfidTagRepository.save(newTag);

        return vehicleMapper.toRfidTagResponse(newTag);
    }

    // Hàm sinh tag_uid đẹp hơn (ví dụ: ETG12345)
    private String generateTagUid() {
        return "ETG" + UUID.randomUUID().toString().replace("-", "").substring(0, 6).toUpperCase();
    }

}
