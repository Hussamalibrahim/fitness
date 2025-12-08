package com.h2lib.user.model.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import lombok.Data;


@Data
public class RegisterRequest {
    @Nullable
    private String username;
    @Nullable
    @Email(message = "invalid Email")
    private String email;
    @Min(value = 6, message = "password most be more than 6 letters")
    private String password;

}
