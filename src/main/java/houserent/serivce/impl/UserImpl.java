package houserent.serivce.impl;

import houserent.config.jwt.JwtService;
import houserent.dto.request.SignUpRequest;
import houserent.dto.response.SimpleResponse;
import houserent.entity.User;
import houserent.repository.UserRepo;
import houserent.serivce.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import static org.hibernate.query.sqm.tree.SqmNode.log;

@RequiredArgsConstructor
@Service
public class UserImpl implements UserService {
    private final UserRepo userRepo;
    private final JwtService jwtService;


    @Override
    public SimpleResponse register(SignUpRequest signUpRequest) {

        User user = new User();

        user.setName(signUpRequest.getName());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(signUpRequest.getPassword());
        user.setPhoneNumber(signUpRequest.getPhoneNumber());

        String newToken = jwtService.createToken(user);
        log.info( user.getName() + " successfully saved!");
        return SimpleResponse
                .builder()
                .httpStatus(HttpStatus.OK)
                .message("Saved")
                .build();
    }
}
