package com.example.restapi.service;

import com.example.restapi.dto.UserRegistrationDto;
import com.example.restapi.model.Role;
import com.example.restapi.model.User;
import com.example.restapi.repository.RoleRepository;
import com.example.restapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id){
        return userRepository.findById(id);
    }

    public User saveUser(User user){
        return userRepository.save(user);
    }

    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }

    public User assignRoleToUser(Long userId, String roleName){
        Optional<User> optionalUser = userRepository.findById(userId);
        Role role = roleRepository.findByName(roleName);

        if (optionalUser.isPresent()){
            User user = optionalUser.get();
            Set<Role> roles = user.getRoles();
            if (roles == null){
                roles = new HashSet<>();
            }
            roles.add(role);
            return userRepository.save(user);
        }else {
            throw new RuntimeException("User or Role Not Found");
        }
    }

    public User registerUser(UserRegistrationDto registrationDto){
        User user = new User();
        user.setName(registrationDto.getName());
        user.setEmail(registrationDto.getEmail());
        user.setUsername(registrationDto.getUsername());
        user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));

        //assign default role
        Role role = roleRepository.findByName("USER");
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);
        return userRepository.save(user);
    }

    public Optional<User> loginUser(String username, String password){
        Optional<User> userByName = userRepository.findByUsername(username);
        if (userByName.isPresent()){
            User user = userByName.get();
            if (passwordEncoder.matches(password, user.getPassword())){
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

}
