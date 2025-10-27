package com.example.ETCSystem.services;


import com.example.ETCSystem.dto.request.UserRequest;
import com.example.ETCSystem.dto.response.UserResponse;
import com.example.ETCSystem.entities.User;
import com.example.ETCSystem.enums.AccountStatus;
import com.example.ETCSystem.exceptions.AppException;
import com.example.ETCSystem.exceptions.ErrorCode;
import com.example.ETCSystem.mapper.UserMapper;
import com.example.ETCSystem.repositories.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
    UserMapper userMapper;
    UserRepository userRepository;

    public UserResponse createUser(UserRequest userRequest) {

        if(!userRequest.getPassword().equals(userRequest.getConfirmPassword())) {
            throw new AppException(ErrorCode.PASSWORDS_DO_NOT_MATCH);
        }
        if(userRepository.existsByUsername(userRequest.getUsername())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        User user = userMapper.toUser(userRequest);
        user.setEmail(userRequest.getUsername());
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        log.info("user in createUser{}", user);

        return userMapper.toUserResponse(userRepository.save(user));
    }

//    kích hoạt tài khoản
    public void activateUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        
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

    public void findByUsername(String username) {
        userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
    }

    public UserResponse userLogin(UserRequest userRequest) {
        User user = userRepository.findByUsername(userRequest.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        if(!passwordEncoder.matches(userRequest.getPassword(), user.getPassword())) {
            throw new AppException(ErrorCode.INVALID_CREDENTIALS);
        }
        return userMapper.toUserResponse(user);
    }
}
