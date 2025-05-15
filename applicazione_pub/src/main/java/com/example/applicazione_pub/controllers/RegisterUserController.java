package com.example.applicazione_pub.controllers;

import com.example.applicazione_pub.entities.RegisterUser;
import com.example.applicazione_pub.services.RegisterUserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/register")
public class RegisterUserController {

    @Autowired
    RegisterUserServices registerUserServices;

    @PostMapping("/send")
    public RegisterUser sendRegistration(@RequestBody RegisterUser registerUser){
        return registerUserServices.sendRegistration(registerUser);
    }
}
