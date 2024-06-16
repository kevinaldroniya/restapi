package com.example.restapi.service;

import com.example.restapi.dto.UserLoginDto;
import com.example.restapi.dto.UserLoginResponseDto;
import com.example.restapi.model.User;
import com.example.restapi.repository.UserRepository;
import com.example.restapi.util.JwtRequestFilter;
import com.example.restapi.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailService customUserDetailService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    public UserLoginResponseDto userLogin(UserLoginDto request) throws Exception {
     try {
         authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                 request.getUsername(), request.getPassword()
         ));
     }catch (BadCredentialsException e){
         throw new Exception("Incorrect username or password");
     }

     final UserDetails userDetails = customUserDetailService
             .loadUserByUsername(request.getUsername());

        Optional<User> user = userRepository.findByUsername(userDetails.getUsername());

        final String jwt = jwtTokenUtil.generateToken(userDetails.getUsername());

     return new UserLoginResponseDto(user.get().getId(), user.get().getEmail(), jwt);
    }


}
