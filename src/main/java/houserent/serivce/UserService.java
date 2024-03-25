package houserent.serivce;

import houserent.dto.request.RentInfoRequest;
import houserent.dto.response.*;
import houserent.dto.request.ReplenishRequest;
import houserent.dto.request.SignInRequest;
import houserent.dto.request.SignUpRequest;

import java.util.List;

public interface UserService {
    SignUpResponse register(SignUpRequest signUpRequest);

    LoginResponse login(SignInRequest signInRequest);

    houserent.dto.response.SimpleResponse replenish(ReplenishRequest replenishRequest);

    SimpleResponse addFavoritePost(Long postId);

    List<FavoritePostsResponse> getAllFavoritePosts();

    SimpleResponse toBook(Long postId, RentInfoRequest rentInfoRequest);
}
