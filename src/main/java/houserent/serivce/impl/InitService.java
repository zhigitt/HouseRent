package houserent.serivce.impl;

import houserent.entity.User;
import houserent.entity.enums.Role;
import houserent.repository.UserRepo;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InitService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

//    @PostConstruct @Transactional
    void saveAdmin(){
        User admin = new User(
                "Admin",
                "admin@gmail.com",
                0,
                passwordEncoder.encode( "1234"),
                "+996701548565",
                Role.ADMIN
        );

        userRepo.save(admin);
    }



}