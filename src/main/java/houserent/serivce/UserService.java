package houserent.serivce;

import houserent.dto.request.SignInRequest;
import houserent.dto.request.SignUpRequest;
import houserent.dto.response.LoginResponse;
import houserent.dto.response.SimpleResponse;

public interface UserService {
    SimpleResponse register(SignUpRequest signUpRequest);

    LoginResponse login(SignInRequest signInRequest);

}
