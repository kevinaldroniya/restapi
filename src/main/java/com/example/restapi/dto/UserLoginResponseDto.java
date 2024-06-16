package com.example.restapi.dto;

public class UserLoginResponseDto {
    private final Long id;
    
    private final String email;

    private final String token;


    public UserLoginResponseDto(Long id, String email, String token) {
        this.id = id;
        this.email = email;
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

}
