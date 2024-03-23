package houserent.serivce;

import houserent.dto.SimpleResponse;
import houserent.dto.request.ReplenishRequest;
import houserent.dto.request.SignInRequest;
import houserent.dto.request.SignUpRequest;
import houserent.dto.response.LoginResponse;
import houserent.dto.response.SignUpResponse;

public interface UserService {
    SignUpResponse register(SignUpRequest signUpRequest);

    LoginResponse login(SignInRequest signInRequest);

    houserent.dto.SimpleResponse replenish(ReplenishRequest replenishRequest);
}
