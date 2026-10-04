package com.h2lib.user.service.imp;

import com.h2lib.user.Repository.UserRepository;
import com.h2lib.user.model.User;
import com.h2lib.user.model.dto.LoginRequest;
import com.h2lib.user.model.dto.RegisterRequest;
import com.h2lib.user.model.dto.UserDto;
import com.h2lib.user.model.enumeration.Role;
import com.h2lib.user.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.h2lib.user.model.mapper.UserMapper.*;

@AllArgsConstructor
@Service
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserDto login(@Valid LoginRequest loginRequest) {
        Optional<User> user = userRepository.findUsersByEmail(loginRequest.getEmail());
        if (user.isEmpty()) {
            throw new IllegalArgumentException("invalid credentials");
        }
        if (!user.get().getPassword().equals(loginRequest.getPassword())) {
            throw new IllegalArgumentException("invalid credentials");
        }

        return convertToDto(user.get());
    }

    @Override
    public UserDto register(@Valid RegisterRequest registerRequest) {

        if (userRepository.existsUserByEmail(registerRequest.getEmail())) {
            throw new IllegalArgumentException("Email already in use");
        }

        User credentials = new User();
        credentials.setUsername(registerRequest.getUsername());
        credentials.setEmail(registerRequest.getEmail());
//        credentials.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        credentials.setPassword(registerRequest.getPassword());
        credentials.setRole(Role.USER);


        return convertToDto(userRepository.save(credentials));
    }

    @Override
    public UserDto getUserProfile(Long userId) {
        return convertToDto(userRepository.findUsersById(userId));
    }

    @Override
    public Boolean userValidate(Long userId) {
        return userRepository.existsById(userId);
    }


    public Long findByKeycloakId(String keycloakId) {
        User user = userRepository.findByKeycloakId(keycloakId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return user.getId();
    }
}
