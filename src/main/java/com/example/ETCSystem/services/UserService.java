package com.example.ETCSystem.services;


import com.example.ETCSystem.dto.request.UserRequest;
import com.example.ETCSystem.dto.response.UserResponse;
import com.example.ETCSystem.entities.User;
import com.example.ETCSystem.enums.AccountStatus;
import com.example.ETCSystem.enums.Role;
import com.example.ETCSystem.exceptions.AppException;
import com.example.ETCSystem.exceptions.ErrorCode;
import com.example.ETCSystem.mapper.UserMapper;
import com.example.ETCSystem.repositories.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
    UserMapper userMapper;
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;

    public UserResponse createUser(UserRequest userRequest) {

        if(!userRequest.getPassword().equals(userRequest.getConfirmPassword())) {
            throw new AppException(ErrorCode.PASSWORDS_DO_NOT_MATCH);
        }
        if(userRepository.existsByUsername(userRequest.getUsername())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        User user = userMapper.toUser(userRequest);
        user.setEmail(userRequest.getUsername());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));

        HashSet<String> roles = new HashSet<>();
        roles.add(Role.CUSTOMER.name());
        user.setRoles(roles);

        log.info("user in createUser{}", user);

        return userMapper.toUserResponse(userRepository.save(user));
    }

//    kích hoạt tài khoản
    public void activateUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        
        log.info("user in activateUser{}", user);

        user.setEnabled(true);
        user.setStatus(AccountStatus.ACTIVE);
        try{
            userRepository.save(user);
        }catch(Exception e){
            throw new AppException(ErrorCode.UPDATE_USER_FAILED, e);
        }

    }


    public List<UserResponse> getAllUsers() {

        List<User> users = userRepository.findAll();
        return userMapper.toUserResponseList(users);
    }

    public UserResponse getMyInfo() {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        User user = userRepository.findByUsername(name)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        return userMapper.toUserResponse(user);
    }

    public UserResponse updateUserInfo(UserRequest userRequest) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

//        update user info
        userMapper.updateUserFromRequest(userRequest, user);
        try{
            user = userRepository.save(user);
        }catch(Exception e){
            throw new AppException(ErrorCode.UPDATE_USER_FAILED, e);
        }
        return userMapper.toUserResponse(user);

    }

    public void findByUsername(String username) {
        userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
    }


}
