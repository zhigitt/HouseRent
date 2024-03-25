package houserent.serivce;

import houserent.dto.response.SimpleResponse;
import houserent.dto.request.ReplenishRequest;
import houserent.dto.request.SignInRequest;
import houserent.dto.request.SignUpRequest;
import houserent.dto.response.LoginResponse;
import houserent.dto.response.PostResponseAll;
import houserent.dto.response.SignUpResponse;

import java.util.List;

public interface UserService {
    SignUpResponse register(SignUpRequest signUpRequest);

    LoginResponse login(SignInRequest signInRequest);

    houserent.dto.SimpleResponse replenish(ReplenishRequest replenishRequest);

    SimpleResponse addFavoritePost(Long postId);

    List<PostResponseAll> getAllFavoritePosts();
    SimpleResponse replenish(ReplenishRequest replenishRequest);
}
