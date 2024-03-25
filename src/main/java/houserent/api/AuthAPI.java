package houserent.api;

import houserent.dto.request.SignInRequest;
import houserent.dto.request.SignUpRequest;
import houserent.dto.response.LoginResponse;
import houserent.dto.response.SignUpResponse;
import houserent.serivce.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthAPI {
    private final UserService userService;

    @PostMapping("/signUp")
    SignUpResponse signUp(@RequestBody @Valid SignUpRequest signUpRequest){
        return userService.register(signUpRequest);
    }

    @GetMapping("/signIn")
    LoginResponse sigIn(@RequestBody @Valid SignInRequest signInRequest){
        return userService.login(signInRequest);
    }



}
