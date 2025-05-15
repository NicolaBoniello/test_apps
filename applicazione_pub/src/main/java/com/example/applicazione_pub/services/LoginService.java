package com.example.applicazione_pub.services;

import com.example.applicazione_pub.entities.RegisterUser;
import com.example.applicazione_pub.repositories.IRegisterUserRepository;
import com.example.applicazione_pub.request.LoginRequest;
import com.example.applicazione_pub.response.LoginResponse;
import com.example.applicazione_pub.utility.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginService {

    @Autowired
    IRegisterUserRepository registerUserRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtUtil jwtUtil;

    public LoginResponse authenticate(LoginRequest loginRequest){


        RegisterUser user = registerUserRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("Errore: utente non trovato."));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new RuntimeException("Errore: password non valida.");
        }

        String token = jwtUtil.generateToken(user.getEmail());

        return new LoginResponse("Login effettuato con successo",user.getEmail(), token);





    }


}
