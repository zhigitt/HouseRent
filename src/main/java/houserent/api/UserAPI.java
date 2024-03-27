package houserent.api;

import houserent.dto.request.RentInfoRequest;
import houserent.dto.request.UpdateRequest;
import houserent.dto.response.FavoritePostsResponse;
import houserent.dto.response.SimpleResponse;
import houserent.dto.request.ReplenishRequest;
import houserent.dto.response.UserResponse;
import houserent.serivce.UserService;
import lombok.RequiredArgsConstructor;
import org.aspectj.apache.bcel.generic.TargetLostException;
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
    SimpleResponse replenish(@RequestBody ReplenishRequest replenishRequest) {
        return userService.replenish(replenishRequest);
    }

    @Secured({"CLIENT", "VENDOR"})
    @PostMapping("/addFavorite/{postId}")
    SimpleResponse addPost(@PathVariable Long postId) {
        return userService.addFavoritePost(postId);
    }

    @Secured({"CLIENT", "VENDOR"})
    @GetMapping("/getAllFavorites")
    List<FavoritePostsResponse> getAll() {
        return userService.getAllFavoritePosts();
    }

    @Secured("CLIENT")
    @PostMapping("/toBook/{postId}")
    SimpleResponse toBook(@PathVariable Long postId,
                          @RequestBody RentInfoRequest rentInfoRequest) {
        return userService.toBook(postId, rentInfoRequest);
    }


    @Secured({"VENDOR", "CLIENT"})
    @PutMapping("/update")
    SimpleResponse update(@RequestBody UpdateRequest updateRequest) {
        return userService.update(updateRequest);
    }

    @Secured("ADMIN")
    @GetMapping("/getAllUsers")
    List<UserResponse> getAllUsers() {
        return userService.getAllUsers();
    }

    @Secured("ADMIN")
    @PostMapping("/delete/{userId}")
    SimpleResponse delete(@PathVariable Long userId){
        return userService.delete(userId);
    }
}
