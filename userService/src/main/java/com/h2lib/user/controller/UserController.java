package com.h2lib.user.controller;

import com.h2lib.user.model.dto.LoginRequest;
import com.h2lib.user.model.dto.RegisterRequest;
import com.h2lib.user.model.dto.UserDto;
import com.h2lib.user.service.UserService;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/user")
@AllArgsConstructor
@SuppressWarnings({"JvmTaintAnalysis"})
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok().body(userService.login(loginRequest));
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@Valid @RequestBody RegisterRequest registerRequest) {
        return ResponseEntity.ok().body(userService.register(registerRequest));
    }
    @GetMapping("{userId}")
    public ResponseEntity<UserDto> getUserProfile(@Nullable @PathVariable Long userId) {
        return ResponseEntity.ok().body(userService.getUserProfile(userId));
    }
    @GetMapping("{userId}/validate")
    public ResponseEntity<Boolean> userValidate(@Nullable @PathVariable Long userId) {
        return ResponseEntity.ok().body(userService.userValidate(userId));
    }
}
