package com.example.ETCSystem.mapper;

import com.example.ETCSystem.dto.response.AdminStationResponse;
import com.example.ETCSystem.entities.Station;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AdminStationMapper {
    AdminStationResponse toAdminStationResponse(Station station);
}
