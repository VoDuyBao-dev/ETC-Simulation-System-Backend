package com.example.ETCSystem.mapper;

import com.example.ETCSystem.dto.response.AdminRfidReaderResponse;
import com.example.ETCSystem.entities.RfidReader;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AdminRfidReaderMapper {
    AdminRfidReaderResponse toAdminRfidReaderResponse(RfidReader rfidReader);
}
