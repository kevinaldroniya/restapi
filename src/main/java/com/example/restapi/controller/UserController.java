package com.example.restapi.controller;

import com.example.restapi.dto.UserDto;
import com.example.restapi.dto.UserRoleAssignmentRequest;
import com.example.restapi.model.User;
import com.example.restapi.service.UserService;
import com.example.restapi.util.UserConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping
    public List<UserDto> getAllUser(){
        List<User> allUsers = userService.getAllUsers();
        return allUsers.stream()
                .map(UserConverter::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public Optional<UserDto> getuserById(@PathVariable Long id){
        Optional<User> userById = userService.getUserById(id);
        return userById.map(UserConverter::convertToDto);
    }

    @PostMapping
    public UserDto addUser(@RequestBody User users){
        User user = userService.saveUser(users);
        return UserConverter.convertToDto(user);
    }

    @DeleteMapping("/{id}")
    public void deleteuser(@PathVariable Long id){
        userService.deleteUser(id);
    }

    @PostMapping("/role")
    public UserDto assignRoleToUser(@RequestBody UserRoleAssignmentRequest request){
        User user = userService.assignRoleToUser(request.getUserId(), request.getRole());
        return UserConverter.convertToDto(user);
    }
}
