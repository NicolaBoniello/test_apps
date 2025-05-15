package com.example.applicazione_pub.services;

import com.example.applicazione_pub.entities.Role;
import com.example.applicazione_pub.repositories.IRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired
    IRoleRepository roleRepository;

    public Role saveRole(Role role){
        return roleRepository.save(role);
    }
}
