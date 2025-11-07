package com.example.ETCSystem.services;

import com.example.ETCSystem.dto.request.AdminUpdateUserRequest;
import com.example.ETCSystem.dto.response.AdminUserResponse;
import com.example.ETCSystem.entities.User;
import com.example.ETCSystem.exceptions.AppException;
import com.example.ETCSystem.exceptions.ErrorCode;
import com.example.ETCSystem.mapper.AdminUserMapper;
import com.example.ETCSystem.repositories.AdminUserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import com.example.ETCSystem.enums.AccountStatus;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminUserService {

    private final AdminUserMapper userMapper;
    private final AdminUserRepository userRepository;

    public Page<AdminUserResponse> getAllUsers(int page, int size) {
        if (page < 0 || size <= 0) {
            throw new AppException(ErrorCode.INVALID_PAGINATION);
        }
        Page<User> userPage = userRepository.findAll(PageRequest.of(page, size));

        List<AdminUserResponse> userResponses = userPage.getContent().stream()
                .map(user -> {
                    return userMapper.toAdminUserResponse(user);
                })
                .collect(Collectors.toList());

        return new PageImpl<>(userResponses, userPage.getPageable(), userPage.getTotalElements());
    }

    public AdminUserResponse updateUserStatus(Long id, AdminUpdateUserRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        if ("ADMIN".equals(userRepository.findUserRoleByUserId(id))) {
            throw new AppException(ErrorCode.CANNOT_CHANGE_ADMIN_STATUS);
        }

        try {
            AccountStatus newStatus = AccountStatus.valueOf(request.getStatus().toUpperCase());
            user.setStatus(newStatus);
        } catch (IllegalArgumentException e) {
            throw new AppException(ErrorCode.INVALID_STATUS);
        }

        userRepository.save(user);

        return userMapper.toAdminUserResponse(user);
    }
}