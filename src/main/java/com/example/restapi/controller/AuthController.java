package com.example.restapi.controller;

import com.example.restapi.dto.UserDto;
import com.example.restapi.dto.UserLoginDto;
import com.example.restapi.dto.UserLoginResponseDto;
import com.example.restapi.dto.UserRegistrationDto;
import com.example.restapi.model.User;
import com.example.restapi.service.AuthService;
import com.example.restapi.service.UserService;
import com.example.restapi.util.UserConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<UserDto> userRegistration(@RequestBody UserRegistrationDto registrationDto){
        User user = userService.registerUser(registrationDto);
        UserDto userDto = UserConverter.convertToDto(user);
        return ResponseEntity.ok(userDto);
    }

    @PostMapping("/signin")
    public ResponseEntity<?> userLogin(@RequestBody UserLoginDto loginDto) throws Exception {
        UserLoginResponseDto response = authService.userLogin(loginDto);
        return ResponseEntity.ok(response);
    }

}
