package houserent.serivce;

import houserent.dto.request.*;
import houserent.dto.response.*;

import java.util.List;

public interface UserService {
    SignUpResponse register(SignUpRequest signUpRequest);

    LoginResponse login(SignInRequest signInRequest);

    houserent.dto.response.SimpleResponse replenish(ReplenishRequest replenishRequest);

    SimpleResponse addFavoritePost(Long postId);

    List<FavoritePostsResponse> getAllFavoritePosts();

    SimpleResponse toBook(Long postId, RentInfoRequest rentInfoRequest);

    SimpleResponse update(UpdateRequest updateRequest);

    List<UserResponse> getAllUsers();

    SimpleResponse delete(Long userId);
}
