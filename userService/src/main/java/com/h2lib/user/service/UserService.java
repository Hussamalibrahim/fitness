package com.h2lib.user.service;

import com.h2lib.user.model.dto.LoginRequest;
import com.h2lib.user.model.dto.RegisterRequest;
import com.h2lib.user.model.dto.UserDto;

public interface UserService {

    UserDto login(LoginRequest loginRequest);

    UserDto register(RegisterRequest registerRequest);

    UserDto getUserProfile(Long userId);

    Boolean userValidate(Long userId);
}
