package houserent.api;

import houserent.dto.request.SignInRequest;
import houserent.dto.request.SignUpRequest;
import houserent.dto.response.LoginResponse;
import houserent.dto.response.SimpleResponse;
import houserent.serivce.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserAPI {
    private final UserService userService;

    @PostMapping("/signUp")
    SimpleResponse signUp(@RequestBody SignUpRequest signUpRequest){
        return userService.register(signUpRequest);
    }

    @GetMapping("/signIn")
    LoginResponse sigIn(@RequestBody SignInRequest signInRequest){
        return userService.login(signInRequest);
    }

    



}
