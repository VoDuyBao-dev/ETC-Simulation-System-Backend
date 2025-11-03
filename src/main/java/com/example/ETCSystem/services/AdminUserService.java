package com.example.ETCSystem.services;

import com.example.ETCSystem.dto.request.AdminUpdateUserRequest;
import com.example.ETCSystem.dto.response.UserResponse;
import com.example.ETCSystem.entities.User;
import com.example.ETCSystem.exceptions.AppException;
import com.example.ETCSystem.exceptions.ErrorCode;
import com.example.ETCSystem.mapper.UserMapper;
import com.example.ETCSystem.repositories.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.ETCSystem.enums.AccountStatus;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AdminUserService {
    UserMapper userMapper;
    UserRepository userRepository;

    // Lấy danh sách tất cả người dùng, sắp xếp theo ngày tạo mới nhất
    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAllByOrderByCreatedAtDesc();

        if (users == null || users.isEmpty()) {
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }
        return userMapper.toUserResponseList(users);
    }

    // Lấy thông tin người dùng theo id
    public UserResponse getUserById(Long id) {
        if (id == null || id <= 0) {
            throw new AppException(ErrorCode.INVALID_KEY);
        }
        User user = userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        return userMapper.toUserResponse(user);
    }

    // Cập nhật trạng thái tài khoản của người dùng
    public UserResponse updateUserStatus(Long id, String newStatus) {
        if (id == null || id <= 0) {
            throw new AppException(ErrorCode.INVALID_KEY);
        }

        User user = userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        // Kiểm tra xem status hợp lệ không
        AccountStatus statusEnum;
        try {
            statusEnum = AccountStatus.valueOf(newStatus.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new AppException(ErrorCode.INVALID_STATUS);
        }

        // Không cho admin bị BLOCKED
        if (user.getRoles() != null && user.getRoles().contains("ADMIN") && statusEnum == AccountStatus.BLOCKED) {
            throw new AppException(ErrorCode.CANNOT_DELETE_ADMIN);
        }

        // Nếu status không thay đổi thì bỏ qua
        if (user.getStatus() == statusEnum) {
            throw new AppException(ErrorCode.INVALID_STATUS);
        }

        // Cập nhật và lưu
        user.setStatus(statusEnum);
        userRepository.save(user);

        return userMapper.toUserResponse(user);
    }

    // Admin cập nhật thông tin người dùng 
    public UserResponse updateUserInfo(Long id, AdminUpdateUserRequest request) {
        if (id == null || id <= 0) {
            throw new AppException(ErrorCode.INVALID_KEY);
        }

        User user = userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        // Kiểm tra trùng email (nếu thay đổi)
        if (request.getEmail() != null && !request.getEmail().equalsIgnoreCase(user.getEmail())) {
            boolean exists = userRepository.existsByEmail(request.getEmail());
            if (exists) {
                throw new AppException(ErrorCode.INVALID_KEY); // Có thể tạo mã lỗi riêng EMAIL_ALREADY_EXISTS
            }
        }

        // Cập nhật các trường còn lại
        if (request.getFullName() != null)
            user.setFullName(request.getFullName());
        if (request.getEmail() != null)
            user.setEmail(request.getEmail());
        if (request.getPhone() != null)
            user.setPhone(request.getPhone());
        if (request.getAddress() != null)
            user.setAddress(request.getAddress());

        userRepository.save(user);

        return userMapper.toUserResponse(user);
    }
}