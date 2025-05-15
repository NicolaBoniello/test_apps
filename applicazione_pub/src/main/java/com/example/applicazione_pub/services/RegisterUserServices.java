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


@Service
public class RegisterUserServices {


    private final IRegisterUserRepository registerUserRepository;
    private final EmailService emailService;
    private final IRoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegisterUserServices(IRegisterUserRepository registerUserRepository, EmailService emailService, IRoleRepository roleRepository, PasswordEncoder passwordEncoder){
        this.registerUserRepository = registerUserRepository;
        this.emailService = emailService;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public RegisterUser sendRegistration(RegisterUser registerUser){

        //Recupera il ruolo predefinito da assegnare all'utente
        Role defaultRole = roleRepository.findByRoleName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException(
                        "Errore: Ruole non trovato"
                ));

        Set<Role> roles = new HashSet<>();
        roles.add(defaultRole);

        //Crea nuovo utente con i dati forniti
        RegisterUser registeredUser = new RegisterUser(
                registerUser.getName(),
                registerUser.getSurname(),
                registerUser.getEmail(),
                passwordEncoder.encode(registerUser.getPassword()),
                false,
                roles
        );

        //Salva l'utente nel database
        registerUserRepository.save(registerUser);

        //Invia una email di conferma all'utente
        sendConfirmationEmail(registerUser);

        return registeredUser;
        }

        private void sendConfirmationEmail(RegisterUser registerUser){
            String subject = "Benvenuto " + registerUser.getName() + "!";
            String body = "Ciao " + registerUser.getName() + ",\n\n" + 
            "Grazie per esserti registrato nella nostra piattaforma! \n" +
            "tuo account è stato creato con successo. \n\n" + 
            "Cordiali saluti, \n Il team";

            emailService.sendEmail(registerUser.getEmail(), subject, body);
        }
}
