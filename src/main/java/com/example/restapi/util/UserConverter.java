package com.example.restapi.util;

import com.example.restapi.dto.UserDto;
import com.example.restapi.model.Role;
import com.example.restapi.model.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserConverter {

    public static UserDto convertToDto(User user){
        UserDto userDto = new UserDto();
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        List<String> roles = user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toList());
        userDto.setRoles(roles);
        return userDto;
    }
}
