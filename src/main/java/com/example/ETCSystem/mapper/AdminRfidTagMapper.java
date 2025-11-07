package com.example.ETCSystem.mapper;

import com.example.ETCSystem.dto.response.AdminRfidTagResponse;
import com.example.ETCSystem.entities.RfidTag;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AdminRfidTagMapper {
    AdminRfidTagResponse toAdminRfidTagResponse(RfidTag rfidTag);

}
