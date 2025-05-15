package com.example.applicazione_pub.controllers;

import com.example.applicazione_pub.entities.Role;
import com.example.applicazione_pub.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    RoleService roleService;

    @PostMapping("/add-role")
    public Role addRole(@RequestBody Role role){
        return roleService.saveRole(role);
    }
}
