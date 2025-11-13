package com.example.ETCSystem.mapper;

import com.example.ETCSystem.dto.response.AdminUserResponse;
import com.example.ETCSystem.entities.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AdminUserMapper {
    AdminUserResponse toAdminUserResponse(User user);
}
