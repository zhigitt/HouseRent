package houserent.api;

import houserent.dto.SimpleResponse;
import houserent.dto.request.ReplenishRequest;
import houserent.dto.request.SignUpRequest;
import houserent.repository.UserRepo;
import houserent.serivce.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserAPI {

    private final UserService userService;

    @Secured("CLIENT")
    @PostMapping("/replenish")
    SimpleResponse replenish(@RequestBody ReplenishRequest replenishRequest){
        return userService.replenish(replenishRequest);
    }


}
