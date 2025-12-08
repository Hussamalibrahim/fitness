package com.h2lib.user.model.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class LoginRequest {
    @Email(message = "invalid credential")
    @Nullable
    private String email;
    @Min(value = 6, message = "invalid password")
    private String password;
}
