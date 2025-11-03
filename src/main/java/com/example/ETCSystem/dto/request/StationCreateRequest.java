package com.example.ETCSystem.dto.request;

import com.example.ETCSystem.enums.StationStatus;
// import com.example.ETCSystem.validation.UniqueStationCode;
// import com.example.ETCSystem.validation.ValidCoordinates;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
// @ValidCoordinates // custom: latitude/longitude hợp lệ & không (0,0)
public class StationCreateRequest {

    @NotBlank(message = "Tên trạm không được để trống")
    @Size(max = 255, message = "Tên trạm tối đa 255 ký tự")
    private String name;

    @NotBlank(message = "Địa chỉ không được để trống")
    @Size(max = 255, message = "Địa chỉ tối đa 255 ký tự")
    private String address;

    @NotBlank(message = "Mã trạm không được để trống")
    @Size(min = 3, max = 20, message = "Mã trạm dài 3-20 ký tự")
    @Pattern(regexp = "^[A-Z0-9_-]+$", message = "Mã trạm chỉ cho phép A-Z, 0-9, gạch dưới, gạch ngang")
    // @UniqueStationCode(message = "Mã trạm đã tồn tại")
    private String code;

    @NotNull(message = "Vĩ độ không được null")
    @DecimalMin(value = "-90.0", message = "Vĩ độ phải từ -90 đến 90")
    @DecimalMax(value = "90.0", message = "Vĩ độ phải từ -90 đến 90")
    private Double latitude;

    @NotNull(message = "Kinh độ không được null")
    @DecimalMin(value = "-180.0", message = "Kinh độ phải từ -180 đến 180")
    @DecimalMax(value = "180.0", message = "Kinh độ phải từ -180 đến 180")
    private Double longitude;

    @NotNull(message = "Trạng thái không được null")
    private StationStatus status;
}
