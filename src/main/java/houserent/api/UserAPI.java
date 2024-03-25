package houserent.api;

import houserent.dto.response.SimpleResponse;
import houserent.dto.request.ReplenishRequest;
import houserent.dto.response.PostResponseAll;
import houserent.serivce.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @Secured({"CLIENT", "VENDOR"})
    @PostMapping("/addFavorite/{postId}")
    SimpleResponse addPost(@PathVariable Long postId ){
        return userService.addFavoritePost(postId);
    }

    @Secured({"CLIENT", "VENDOR"})
    @GetMapping("/getAllFavorites")
    List<PostResponseAll> getAll(){
        return userService.getAllFavoritePosts();
    }
//    @Secured("CLIENT")
//    @PutMapping("/addFavorite")
//    SimpleResponse addPost(@PathVariable Long postId ){
//        return userService.addFavoritePost(postId);
//    }
//

}
