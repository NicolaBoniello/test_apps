package com.example.applicazione_pub.services;

import com.example.applicazione_pub.entities.RegisterUser;
import com.example.applicazione_pub.entities.Role;
import com.example.applicazione_pub.repositories.IRegisterUserRepository;
import com.example.applicazione_pub.repositories.IRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
public class RegisterUserServices {

    @Autowired
    IRegisterUserRepository registerUserRepository;

    @Autowired
    EmailService emailService;

    @Autowired
    IRoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public RegisterUser sendRegistration(RegisterUser registerUser){

        Role defaultRole = roleRepository.findByRoleName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));

        Set<Role> role = new HashSet<>();
        role.add(defaultRole);

        RegisterUser registeredUser = new RegisterUser(
                registerUser.getName(),
                registerUser.getSurname(),
                registerUser.getEmail(),
                passwordEncoder.encode(registerUser.getPassword()),
                false,
                role
        );

        registerUserRepository.save(registeredUser);

        String subject = "Benvenuto " + registeredUser.getName() + "!";
        String body = "Ciao " + registeredUser.getName() + ",\n\n" +
                "Grazie per esserti registrato nella nostra piattaforma! Il tuo account è stato creato con successo.\n\n" +
                "Cordiali saluti,\nIl Team";

        emailService.sendEmail(registeredUser.getEmail(), subject, body);

        return registerUserRepository.save(registeredUser);

    }
}
