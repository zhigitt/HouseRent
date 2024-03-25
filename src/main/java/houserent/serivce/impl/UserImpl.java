package houserent.serivce.impl;

import houserent.config.jwt.JwtService;
import houserent.dto.response.SimpleResponse;
import houserent.dto.request.ReplenishRequest;
import houserent.dto.request.SignInRequest;
import houserent.dto.request.SignUpRequest;
import houserent.dto.response.LoginResponse;
import houserent.dto.response.PostResponseAll;
import houserent.dto.response.SignUpResponse;
import houserent.entity.Post;
import houserent.entity.User;
import houserent.entity.enums.Role;
import houserent.exception.ForbiddenException;
import houserent.exception.NotFoundException;
import houserent.repository.PostRepository;
import houserent.repository.UserRepo;
import houserent.serivce.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.hibernate.query.sqm.tree.SqmNode.log;

@RequiredArgsConstructor
@Service
public class UserImpl implements UserService {
    private final UserRepo userRepo;
    private final PostRepository postRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public SignUpResponse register(SignUpRequest signUpRequest) {
        boolean exists = userRepo.existsByEmail(signUpRequest.getEmail());
        if (exists) throw new NotFoundException("Email : " + signUpRequest.getEmail() + " already exist");

        User user = new User();

        user.setName(signUpRequest.getName());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        user.setCard(signUpRequest.getCard());
        user.setPhoneNumber(signUpRequest.getPhoneNumber());
        user.setRole(signUpRequest.getRole());


        userRepo.save(user);

        String newToken = jwtService.createToken(user);
        log.info(user.getName() + " successfully saved!");
        return SignUpResponse
                .builder()
                .token(newToken)
                .httpStatus(HttpStatus.OK)
                .message("Saved")
                .build();
    }

    @Override
    public LoginResponse login(SignInRequest signInRequest) {
        User user = userRepo.findByEmail(signInRequest.getEmail()).orElseThrow(() ->
                new NotFoundException("User with email: " + signInRequest.getEmail() + " not found!"));

        String encodePassword = user.getPassword();
        String password = signInRequest.getPassword();

        boolean matches = passwordEncoder.matches(password, encodePassword);

        if (!matches) throw new RuntimeException("Invalid password");

        String token = jwtService.createToken(user);
        return LoginResponse.builder()
                .token(token)
                .id(user.getId())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }

    @Override
    @Transactional
    public houserent.dto.SimpleResponse replenish(ReplenishRequest replenishRequest) {
        User user = getCurrentUser();
    public SimpleResponse replenish(ReplenishRequest replenishRequest) {
        User user =getCurrentUser();

        int card = getCurrentUser().getCard();
        card += replenishRequest.getCard();

        getCurrentUser().setCard(card);

        return SimpleResponse
                .builder()
                .httpStatus(HttpStatus.OK)
                .message(" + " + replenishRequest.getCard())
                .build();
    }

    @Override
    @Transactional
    public SimpleResponse addFavoritePost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new NotFoundException("Myndai ID de post jok!"));
        User user = getCurrentUser();

        if (user.getFavoriteBasket().contains(post)){
            user.getFavoriteBasket().remove(post);
            return SimpleResponse
                    .builder()
                    .httpStatus(HttpStatus.OK)
                    .message("Post korzinadan alyndy!")
                    .build();
        }else {
            user.getFavoriteBasket().add(post);

            return SimpleResponse
                    .builder()
                    .httpStatus(HttpStatus.OK)
                    .message("Post korzinaga koshuldu!")
                    .build();
        }
    }

    @Override
    public List<PostResponseAll> getAllFavoritePosts() {
        User user = getCurrentUser();

        List<Post> posts = new ArrayList<>(user.getFavoriteBasket());
        List<PostResponseAll> postResponseAlls = new ArrayList<>();

        for (Post post : posts) {
            postResponseAlls.add(new PostResponseAll(
                    post.getImage(),
                    post.getDescription(),
                    post.getPersons(),
                    post.get
            ))
        }



        return null;
    }

    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User current = userRepo.getByEmail(email);
        if (current.getRole().equals(Role.VENDOR) || current.getRole().equals(Role.CLIENT))
            return current;
        else throw new ForbiddenException("Forbidden 403");
    }
}
