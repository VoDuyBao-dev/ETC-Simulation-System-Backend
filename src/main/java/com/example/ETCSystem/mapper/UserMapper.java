package com.example.ETCSystem.mapper;

import com.example.ETCSystem.dto.request.UserCreationRequest;
import com.example.ETCSystem.dto.response.UserResponse;
import com.example.ETCSystem.entities.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest request);
    UserResponse toUserResponse(User user);
    List<UserResponse> toUserResponseList(List<User> users);
}
