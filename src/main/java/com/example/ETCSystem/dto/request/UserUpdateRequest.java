package com.example.ETCSystem.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class UserUpdateRequest {


    @NotBlank(message = "FULLNAME_REQUIRED")
    @Size(max = 50, min = 2, message = "FULLNAME_LENGTH_INVALID")
    private String fullname;

    @Pattern(regexp = "^0\\d{9}$", message = "PHONE_INVALID")
    private String phone;

    private String address;


}
