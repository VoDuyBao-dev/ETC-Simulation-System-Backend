package com.example.ETCSystem.dto.request;

import com.example.ETCSystem.enums.StationStatus;
import jakarta.validation.constraints.*;

import lombok.Data;

@Data
public class StationUpdateRequest {

    @NotBlank(message = "Tên trạm không được để trống")
    @Size(max = 255, message = "Tên trạm tối đa 255 ký tự")
    private String name;

    @NotBlank(message = "Địa chỉ không được để trống")
    @Size(max = 255, message = "Địa chỉ tối đa 255 ký tự")
    private String address;

    // code KHÔNG cho phép đổi trong update để giữ tính bất biến/đối soát
    // Nếu thầy yêu cầu cho phép đổi, ta thêm @Pattern & @UniqueStationCode như create.

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
