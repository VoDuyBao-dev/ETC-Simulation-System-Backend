package com.example.ETCSystem.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

    // đăng kí xe mới
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

        // 2. Tạo thẻ E-Tag đầu tiên
        RfidTag tag = new RfidTag();
        tag.setVehicle(vehicle);
        tag.setTagUid(generateTagUid());
        tag.setStatus(TagStatus.ACTIVE);
        tag.setIssuedAt(LocalDateTime.now());
        if (vehicle.getRfidTags() == null) {
            vehicle.setRfidTags(new ArrayList<>());
        }
        vehicle.getRfidTags().add(tag);

        rfidTagRepository.save(tag);
        vehicleRepository.save(vehicle);
        // 3. LOAD LẠI VEHICLE SAU KHI LƯU TAG
        Vehicle fullVehicle = vehicleRepository.findById(vehicle.getId())
                .orElseThrow(() -> new AppException(ErrorCode.VEHICLE_NOT_FOUND));

        return vehicleMapper.toVehicleResponse(fullVehicle);
    }

    // đổi trạng thái của thẻ xe
    public VehicleResponse updateRfidTagStatus(Long vehicleId) {

        User currentUser = userService.getCurrentUser();
        Long userId = currentUser.getUserId();

        // 1. Kiểm tra user
        userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        // 2. Kiểm tra xe
        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new AppException(ErrorCode.VEHICLE_NOT_FOUND));

        // 3. Kiểm tra quyền sở hữu
        if (!vehicle.getUser().getUserId().equals(currentUser.getUserId())) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }

        // 4. Lấy thẻ ACTIVE hiện tại (1 xe chỉ có 1 thẻ ACTIVE)
        RfidTag activeTag = rfidTagRepository
                .findByVehicleIdAndStatus(vehicleId, TagStatus.ACTIVE)
                .orElseThrow(() -> new AppException(ErrorCode.RFID_TAG_NOT_FOUND));

        // 5. Vô hiệu hóa thẻ ACTIVE
        activeTag.setStatus(TagStatus.INACTIVE);
        activeTag.setLastSuccessfulPassage(null);
        activeTag.setLastPassageStationId(null);

        rfidTagRepository.save(activeTag);

        // 6. Không tạo thẻ mới — hàm này chỉ vô hiệu hoá
        // (nếu muốn cấp thẻ mới → dùng API /report-lost-tag)

        // 7. Load lại vehicle để mapper lấy danh sách thẻ mới nhất
        Vehicle fullVehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new AppException(ErrorCode.VEHICLE_NOT_FOUND));

        return vehicleMapper.toVehicleResponse(fullVehicle);
    }

    // Đổi trạng thái của xe và thẻ xe sẽ đổi theo
    public VehicleResponse updateVehicleStatus(Long vehicleId, UpdateVehicleStatusRequest request) {

        User currentUser = userService.getCurrentUser();

        // Kiểm tra user hợp lệ
        User user = userRepository.findById(currentUser.getUserId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new AppException(ErrorCode.VEHICLE_NOT_FOUND));

        // Kiểm tra quyền sở hữu xe
        if (!vehicle.getUser().getUserId().equals(currentUser.getUserId())) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }

        // Parse trạng thái mới của xe
        VehicleStatus newStatus;
        try {
            newStatus = VehicleStatus.valueOf(request.getStatus().toUpperCase());
        } catch (Exception e) {
            throw new AppException(ErrorCode.INVALID_STATUS);
        }

        // Cập nhật trạng thái xe
        vehicle.setStatus(newStatus);
        vehicleRepository.save(vehicle);

        // Lấy danh sách tất cả thẻ của xe
        List<RfidTag> tags = rfidTagRepository.findAllByVehicleId(vehicleId);

        if (tags != null && !tags.isEmpty()) {
            if (newStatus == VehicleStatus.INACTIVE) {
                // ✔ Nếu xe bị vô hiệu hoá → tất cả thẻ ACTIVE đều INACTIVE
                for (RfidTag tag : tags) {
                    if (tag.getStatus() == TagStatus.ACTIVE) {
                        tag.setStatus(TagStatus.INACTIVE);
                    }
                }
                rfidTagRepository.saveAll(tags);

            } else if (newStatus == VehicleStatus.ACTIVE) {
                // ✔ Xe hoạt động → chỉ mở thẻ ACTIVE gần nhất
                RfidTag latestActive = tags.stream()
                        .filter(t -> t.getStatus() == TagStatus.INACTIVE)
                        .sorted((a, b) -> b.getIssuedAt().compareTo(a.getIssuedAt()))
                        .findFirst()
                        .orElse(null);

                if (latestActive != null) {
                    latestActive.setStatus(TagStatus.ACTIVE);
                    rfidTagRepository.save(latestActive);
                }
            }
        }

        // Load lại vehicle sau khi cập nhật
        Vehicle fullVehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new AppException(ErrorCode.VEHICLE_NOT_FOUND));

        return vehicleMapper.toVehicleResponse(fullVehicle);
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
        User currentUser = userService.getCurrentUser();
        Long currentUserId = currentUser.getUserId();
        // Kiểm tra user hợp lệ
        User user = userRepository.findById(currentUserId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new AppException(ErrorCode.VEHICLE_NOT_FOUND));

        if (!vehicle.getUser().getUserId().equals(currentUserId)) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }

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
        return "E" + UUID.randomUUID().toString().replace("-", "").substring(0, 6).toUpperCase();
    }

}
