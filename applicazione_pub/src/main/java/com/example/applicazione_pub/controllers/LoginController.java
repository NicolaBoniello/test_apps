package com.example.applicazione_pub.controllers;

import com.example.applicazione_pub.request.LoginRequest;
import com.example.applicazione_pub.response.LoginResponse;
import com.example.applicazione_pub.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class LoginController {

    @Autowired
    LoginService loginService;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest){
        return loginService.authenticate(loginRequest);
    }
}
