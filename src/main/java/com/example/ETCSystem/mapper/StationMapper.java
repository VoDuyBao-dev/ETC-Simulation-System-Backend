package com.example.ETCSystem.mapper;

import com.example.ETCSystem.dto.request.StationCreateRequest;
import com.example.ETCSystem.dto.request.StationUpdateRequest;
import com.example.ETCSystem.dto.response.StationResponse;
import com.example.ETCSystem.dto.response.StationUpdateStatusResponse;
import com.example.ETCSystem.entities.Station;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StationMapper {

    // Từ DTO tạo mới → Entity
    Station toStation(StationCreateRequest request);

    // Cập nhật Entity bằng DTO update
    void updateStation(@MappingTarget Station station, StationUpdateRequest request);

    // Từ Entity → Response
    StationResponse toStationResponse(Station station);

    // Danh sách Entity → danh sách Response
    List<StationResponse> toStationResponseList(List<Station> stations);

    StationUpdateStatusResponse toStationStatusResponse(Station station);
}
