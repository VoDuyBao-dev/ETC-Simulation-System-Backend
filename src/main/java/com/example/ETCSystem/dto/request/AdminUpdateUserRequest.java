package com.example.ETCSystem.dto.request;

// import com.example.ETCSystem.enums.AccountStatus;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminUpdateUserRequest {

    // @NotNull(message = "USER_REQUIRED")
    // private Long userId;

    @NotNull(message = "STATUS_REQUIRED")
    private String status;
}
