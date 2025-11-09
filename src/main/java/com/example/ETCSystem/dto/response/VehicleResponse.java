package com.example.ETCSystem.dto.response;

// import com.example.ETCSystem.enums.TagStatus;
// import com.example.ETCSystem.enums.VehicleType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehicleResponse {
    private String plateNumber;
    private String tagUid;
    private String tagStatus;
    private String vehicleType;
}
