package com.example.applicazione_pub.repositories;

import com.example.applicazione_pub.entities.RegisterUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IRegisterUserRepository extends JpaRepository<RegisterUser, Long> {

    Optional<RegisterUser> findByEmail(String email);
}
