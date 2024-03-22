package houserent.api;

import houserent.dto.request.SignUpRequest;
import houserent.dto.response.SimpleResponse;
import houserent.serivce.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserAPI {
    private final UserService userService;

    @PostMapping("/signUp")
    SimpleResponse signUp(@RequestBody SignUpRequest signUpRequest){
        return userService.register(signUpRequest);
    }

    



}
