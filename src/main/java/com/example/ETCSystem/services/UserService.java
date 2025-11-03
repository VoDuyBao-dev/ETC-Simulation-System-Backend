package com.example.ETCSystem.services;

import com.example.ETCSystem.dto.request.AdminUpdateUserRequest;
import com.example.ETCSystem.dto.request.UserCreationRequest;
import com.example.ETCSystem.dto.response.UserResponse;
import com.example.ETCSystem.entities.User;
import com.example.ETCSystem.exceptions.AppException;
import com.example.ETCSystem.exceptions.ErrorCode;
import com.example.ETCSystem.mapper.UserMapper;
import com.example.ETCSystem.repositories.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import com.example.ETCSystem.enums.Role;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
    UserMapper userMapper;
    UserRepository userRepository;

    public UserResponse createUser(UserCreationRequest userCreationRequest) {
        if (userRepository.existsByUsername(userCreationRequest.getUsername())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        User user = userMapper.toUser(userCreationRequest);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userMapper.toUserResponse(userRepository.save(user));
    }

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
    public UserResponse updateUserStatus(Long id, boolean status) {
        if (id == null || id <= 0) {
            throw new AppException(ErrorCode.INVALID_KEY);
        }

        User user = userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        user.setIsActive(status); // hàm setter cho thuộc tính status của lớp user
        userRepository.save(user);

        return userMapper.toUserResponse(user);
    }


    // Cập nhật thông tin người dùng (Admin)
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

        // Kiểm tra role hợp lệ
        if (request.getRole() != null) {
            try {
                Role newRole = Role.valueOf(request.getRole().toUpperCase());
                user.setRole(newRole);
            } catch (IllegalArgumentException e) {
                throw new AppException(ErrorCode.INVALID_KEY);
            }
        }

        // Cập nhật các trường còn lại
        if (request.getFullName() != null) user.setFullName(request.getFullName());
        if (request.getEmail() != null) user.setEmail(request.getEmail());
        if (request.getPhone() != null) user.setPhone(request.getPhone());
        if (request.getAddress() != null) user.setAddress(request.getAddress());
        if (request.getIsActive() != null) user.setIsActive(request.getIsActive());

        userRepository.save(user);

        return userMapper.toUserResponse(user);
    }
}
