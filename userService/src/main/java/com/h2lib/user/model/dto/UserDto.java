package com.h2lib.user.model.dto;

import com.h2lib.user.model.enumeration.Role;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDto {
    private Long id;
    private String username;
    @Email(message = "invalid credential")
    @Nullable
    private String email;
    @Min(value = 6, message = "password most be more than 6 letters")
    private String password;
    private Role role = Role.USER;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
