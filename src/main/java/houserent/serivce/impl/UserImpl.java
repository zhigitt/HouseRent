package houserent.serivce.impl;

import houserent.config.jwt.JwtService;
import houserent.dto.request.SignInRequest;
import houserent.dto.request.SignUpRequest;
import houserent.dto.response.LoginResponse;
import houserent.dto.response.SimpleResponse;
import houserent.entity.User;
import houserent.entity.enums.Role;
import houserent.exception.ForbiddenException;
import houserent.exception.NotFoundException;
import houserent.repository.UserRepo;
import houserent.serivce.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static org.hibernate.query.sqm.tree.SqmNode.log;

@RequiredArgsConstructor
@Service
public class UserImpl implements UserService {
    private final UserRepo userRepo;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public SimpleResponse register(SignUpRequest signUpRequest) {
        boolean exists = userRepo.existsByEmail(signUpRequest.getEmail());
        if (exists) throw new NotFoundException("Email : " + signUpRequest.getEmail() + " already exist");

        User user = new User();

        user.setName(signUpRequest.getName());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        user.setCard(signUpRequest.getCard());
        user.setPhoneNumber(signUpRequest.getPhoneNumber());
        user.setRole(signUpRequest.getRole());


        userRepo.save(user);

        String newToken = jwtService.createToken(user);
        log.info( user.getName() + " successfully saved!");
        return SimpleResponse
                .builder()
                .httpStatus(HttpStatus.OK)
                .message("Saved")
                .build();
    }

    @Override
    public LoginResponse login(SignInRequest signInRequest) {
        User user = userRepo.findByEmail(signInRequest.getEmail()).orElseThrow(() ->
                new NotFoundException("User with email: " + signInRequest.getEmail() + " not found!"));

        String encodePassword = user.getPassword();
        String password =signInRequest.getPassword();

        boolean matches = passwordEncoder.matches(password, encodePassword);

        if (!matches) throw new RuntimeException("Invalid password");

        String token = jwtService.createToken(user);
        return LoginResponse.builder()
                .token(token)
                .id(user.getId())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }





    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User current = userRepo.getByEmail(email);
        if (current.getRole().equals(Role.VENDOR) || current.getRole().equals(Role.CLIENT))
            return current;
        else throw new ForbiddenException("Forbidden 403");
    }
}
