package com.h2lib.user.model.mapper;

import com.h2lib.user.model.User;
import com.h2lib.user.model.dto.UserDto;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UserMapper {

    public static User convertToEntity(UserDto userDto) {
        User user = new User();

        user.setId(userDto.getId());
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setRole(userDto.getRole());
        user.setCreateTime(userDto.getCreateTime());
        user.setUpdateTime(userDto.getUpdateTime());

        return user;
    }
    public static UserDto convertToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        userDto.setRole(user.getRole());
        userDto.setCreateTime(user.getCreateTime());
        userDto.setUpdateTime(user.getUpdateTime());
        return userDto;
    }
}
