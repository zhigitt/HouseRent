package houserent.api;

import houserent.dto.response.SimpleResponse;
import houserent.dto.request.ReplenishRequest;
import houserent.serivce.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

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

//    @Secured("CLIENT")
//    @PutMapping("/addFavorite")
//    SimpleResponse addPost(@PathVariable Long postId ){
//        return userService.addFavoritePost(postId);
//    }
//


}
