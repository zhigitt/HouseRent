package houserent.serivce.impl;

import houserent.config.jwt.JwtService;
import houserent.dto.request.*;
import houserent.dto.response.*;
import houserent.entity.Post;
import houserent.entity.RentInfo;
import houserent.entity.User;
import houserent.entity.enums.Role;
import houserent.exception.ForbiddenException;
import houserent.exception.NotFoundException;
import houserent.repository.PostRepository;
import houserent.repository.RentInfoRepo;
import houserent.repository.UserRepo;
import houserent.serivce.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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
    private final RentInfoRepo rentInfoRepo;

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
    public houserent.dto.response.SimpleResponse replenish(ReplenishRequest replenishRequest) {
        User user =getCurrentUser();

        BigDecimal card = getCurrentUser().getCard();
        card = card.add(replenishRequest.getCard());

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
    public List<FavoritePostsResponse> getAllFavoritePosts() {
        User user = getCurrentUser();

        List<Post> posts = new ArrayList<>(user.getFavoriteBasket());
        List<FavoritePostsResponse> favoritePostsResponses = new ArrayList<>();

        for (Post post : posts) {
            favoritePostsResponses.add(new FavoritePostsResponse(
                    post.getId(),
                    post.getTitle(),
                    post.getImages(),
                    post.getPrice()
            ));
        }

        return favoritePostsResponses;
    }

    @Override
    @Transactional
    public SimpleResponse toBook(Long postId, RentInfoRequest rentInfoRequest) {
        Post post = postRepository.findById(postId).orElseThrow(() ->
                new NotFoundException("Mynda IDde post jok!"));


        User vendor = post.getUsers();
        User currentUser = getCurrentUser();
        RentInfo rentInfo = new RentInfo();

        LocalDate checkIn = rentInfoRequest.getChekin();
        LocalDate checkOut = rentInfoRequest.getChekOut();

        int daysBooking = (int) ChronoUnit.DAYS.between(checkIn, checkOut);

        if (!post.isBook()){
            BigDecimal currentUserCard = currentUser.getCard();
            BigDecimal postPrice = post.getPrice();

            if (currentUserCard.compareTo(postPrice) >= 0){
                BigDecimal total = postPrice.multiply(BigDecimal.valueOf(daysBooking));

                currentUserCard = currentUserCard.add(total);
                currentUser.setCard(currentUserCard);

                BigDecimal vendorCard = vendor.getCard().subtract(total);
                vendor.setCard(vendorCard);


                post.setBook(true);

                rentInfo.setUser(currentUser);
                rentInfo.setPost(post);
                rentInfo.setChekin(rentInfoRequest.getChekin());
                rentInfo.setChekOut(rentInfoRequest.getChekOut());
                rentInfoRepo.save(rentInfo);

                return SimpleResponse
                        .builder()
                        .httpStatus(HttpStatus.OK)
                        .message("Brondoldu: " + post.getTitle()+
                                 "Price: " + postPrice +
                                 "Chek in: " + checkIn +
                                "Chek out: " + checkOut +
                                 "Kundor: " + daysBooking+
                                 "Summa: $" +total)
                        .build();
            }else {
                return SimpleResponse
                        .builder()
                        .httpStatus(HttpStatus.OK)
                        .message("Caratochkada akcha jetishsiz!")
                        .build();
            }

        }else {
            return SimpleResponse
                    .builder()
                    .httpStatus(HttpStatus.OK)
                    .message(post.getTitle() + " bosh emes brondolgon!")
                    .build();

        }
    }

    @Override
    @Transactional
    public SimpleResponse update(UpdateRequest updateRequest) {
        User user = getCurrentUser();

        user.setName(updateRequest.getName());
        user.setPassword(updateRequest.getPassword());
        user.setPhoneNumber(updateRequest.getPhoneNumber());

        return SimpleResponse
                .builder()
                .httpStatus(HttpStatus.OK)
                .message(user.getUsername() + " updated!")
                .build();
    }

    @Override
    public List<UserResponse> getAllUsers() {
       getCurrentUsers();
        List<UserResponse> userResponses = new ArrayList<>();
        List<User> all = userRepo.findAll();

        for (User user1 : all) {
            if (!user1.getRole().equals(Role.ADMIN)) {
                userResponses.add(new UserResponse(
                        user1.getId(),
                        user1.getName(),
                        user1.getRole(),
                        user1.getEmail()
                ));
            }
        }
        return userResponses;
    }

    @Override
    @Transactional
    public SimpleResponse delete(Long userId) {
        getCurrentUsers();
        User user = userRepo.findById(userId).orElseThrow(() ->
                new NotFoundException("Myndai IDde user jok!"));

        if (!user.getRole().equals(Role.ADMIN)){
            userRepo.delete(user);
            userRepo.deleteComment(userId);
            userRepo.deletePost(userId);
            userRepo.deleteRentInfo(userId);


            userRepo.delete(user);
            return SimpleResponse
                    .builder()
                    .httpStatus(HttpStatus.OK)
                    .message("Deleted  " + user.getName())
                    .build();
        }else {
            return SimpleResponse
                    .builder()
                    .httpStatus(HttpStatus.OK)
                    .message("Admin admindi ochuro albait")
                    .build();
        }
    }

    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User current = userRepo.getByEmail(email);
        if (current.getRole().equals(Role.VENDOR) || current.getRole().equals(Role.CLIENT))
            return current;
        else throw new ForbiddenException("Forbidden 403");
    }

    private User getCurrentUsers() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User current = userRepo.getByEmail(email);
        if (current.getRole().equals(Role.ADMIN))
            return current;
        else throw new ForbiddenException("Forbidden 403");
    }
}
