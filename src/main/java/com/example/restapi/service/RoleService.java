package com.example.restapi.service;

import com.example.restapi.model.Role;
import com.example.restapi.repository.RoleRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.Arrays;

@Service
public class RoleService {

    @Autowired
    RoleRepository roleRepository;

    @PostConstruct
    public void initRoles(){
        if (roleRepository.findAll().isEmpty()){
            roleRepository.saveAll(Arrays.asList(
                    new Role("ADMIN"),
                    new Role("USER")
            ));
        }
    }

    public Role saveRole(Role role){
        return roleRepository.save(role);
    }

}
