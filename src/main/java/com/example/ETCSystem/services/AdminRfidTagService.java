package com.example.ETCSystem.services;

import com.example.ETCSystem.dto.request.UpdateRfidTagStatusRequest;
import com.example.ETCSystem.dto.response.AdminRfidTagResponse;
import com.example.ETCSystem.entities.RfidTag;
import com.example.ETCSystem.enums.TagStatus;
import com.example.ETCSystem.exceptions.AppException;
import com.example.ETCSystem.exceptions.ErrorCode;
import com.example.ETCSystem.mapper.AdminRfidTagMapper;
import com.example.ETCSystem.repositories.RfidTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminRfidTagService {

    private final RfidTagRepository rfidTagRepository;
    private final AdminRfidTagMapper adminRfidTagMapper;

    // Lấy danh sách tất cả thẻ RFID
    public List<AdminRfidTagResponse> getAllRfidTags() {
        List<RfidTag> tags = rfidTagRepository.findAll();
        return tags.stream()
                .map(adminRfidTagMapper::toAdminRfidTagResponse)
                .collect(Collectors.toList());
    }

    // Cập nhật trạng thái của một thẻ RFID
    public AdminRfidTagResponse updateRfidTagStatus(Long id, UpdateRfidTagStatusRequest request) {
        RfidTag tag = rfidTagRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.RFID_TAG_NOT_FOUND));

        // Kiểm tra enum hợp lệ (phòng trường hợp request gửi sai)
        TagStatus newStatus;
        try {
            newStatus = TagStatus.valueOf(request.getStatus().name().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new AppException(ErrorCode.INVALID_STATUS);
        }

        tag.setStatus(newStatus);
        RfidTag updated = rfidTagRepository.save(tag);

        return adminRfidTagMapper.toAdminRfidTagResponse(updated);
    }
}
