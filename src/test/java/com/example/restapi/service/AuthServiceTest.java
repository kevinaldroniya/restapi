package com.example.restapi.service;


import com.example.restapi.dto.UserLoginDto;
import com.example.restapi.dto.UserLoginResponseDto;
import com.example.restapi.model.User;
import com.example.restapi.repository.UserRepository;
import com.example.restapi.util.JwtTokenUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


class AuthServiceTest {

    @InjectMocks
    private AuthService authService;

    @Mock
    private JwtTokenUtil jwtTokenUtil;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CustomUserDetailService customUserDetailService;

    @Autowired
    private User user;

    @Autowired
    private UserDetails userDetails;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setId(1L);
        user.setName("Test");
        user.setUsername("test");
        user.setEmail("test@test.com");
        user.setPassword("test123");

        userDetails =  new org.springframework.security.core.userdetails.User(
                user.getUsername(),user.getPassword(),new ArrayList<>()
        );

        Mockito.when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
        Mockito.when(customUserDetailService.loadUserByUsername(user.getUsername())).thenReturn(userDetails);
        Mockito.when(jwtTokenUtil.generateToken(user.getUsername())).thenReturn("fake-jwt-token");
    }

    @Test
    public void testAuthenticationUser() throws Exception {
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, new ArrayList<>());
        Mockito.when(authenticationManager.authenticate(Mockito.any())).thenReturn(authentication);

        UserLoginDto userLoginDto = new UserLoginDto();
        userLoginDto.setUsername(user.getUsername());
        userLoginDto.setPassword(user.getPassword());

        UserLoginResponseDto userLoginResponseDto = authService.userLogin(userLoginDto);

        assertNotNull(userLoginResponseDto);
        assertEquals(user.getId(), userLoginResponseDto.getId());
        assertEquals(user.getEmail(), userLoginResponseDto.getEmail());
        assertEquals("fake-jwt-token",userLoginResponseDto.getToken());
    }


}