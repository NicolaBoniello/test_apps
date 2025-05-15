package com.example.applicazione_pub.services;

import com.example.applicazione_pub.entities.RegisterUser;
import com.example.applicazione_pub.repositories.IRegisterUserRepository;
import com.example.applicazione_pub.request.LoginRequest;
import com.example.applicazione_pub.response.LoginResponse;
import com.example.applicazione_pub.utility.JwtUtil;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
//
@Service
public class LoginService {

    
    private final IRegisterUserRepository registerUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Autowired
    public LoginService(IRegisterUserRepository registerUserRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.registerUserRepository = registerUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

@org.springframework.transaction.annotation.Transactional(readOnly = true) //Imposta la transazione come di sola lettura (non modifica il database);
public LoginResponse authenticate(LoginRequest loginRequest){

    //Verifica se l'utente esiste nel database
    RegisterUser user = registerUserRepository.findByEmail(loginRequest.getEmail())
            .orElseThrow(() -> new RuntimeException(
                "Errore: utente non trovato."
            ));

    //Verifica se la password fornita corrisponde alla password dell'utente nel database
    if(!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())){
        throw new BadCredentialsException(
            "Errore: password non valida."
        );
    }

        //Genere un token JWT per l'utente autenticato
        String token = jwtUtil.generateToken(user.getEmail());

        //Restituisce una risposta di login con il messaggio, l'email e il token
        return new LoginResponse("Login effettutato con successo", user.getEmail(), token);
}


}
