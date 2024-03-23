package houserent.serivce.impl;

import houserent.dto.request.PostRequest;
import houserent.dto.response.PostResponseAlls;
import houserent.dto.response.PostResponseOne;
import houserent.dto.response.SimpleResponse;
import houserent.entity.Address;
import houserent.entity.Post;
import houserent.entity.User;
import houserent.entity.enums.HomeType;
import houserent.entity.enums.Role;
import houserent.repository.AddressRepository;
import houserent.repository.PostRepository;
import houserent.repository.UserRepo;
import houserent.serivce.PostService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final UserRepo userRepo;
    private final AddressRepository addressRepository;

    @Override
    public SimpleResponse save(PostRequest postRequest) {

        User currentUser = getCurrentUser();

        Address address = new Address();
        address.setCity(postRequest.getCity());
        address.setRegion(postRequest.getRegion());
        address.setStreet(postRequest.getStreet());

        Address savedAddress = addressRepository.save(address);

        Post post = new Post();
        post.setTitle(postRequest.getTitle());
        post.setDescription(postRequest.getDescription());
        post.setImage(postRequest.getImage());
        post.setHometype(postRequest.getHometype());
        post.setPrice(postRequest.getPrice());
        post.setPersons(postRequest.getPersons());

        post.setAddress(savedAddress);
        post.setUsers(currentUser);
        postRepository.save(post);

        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Пост успешно сохранен!")
                .build();
    }

    @Override  @Transactional
    public SimpleResponse update(Long postId, PostRequest postRequest) {
        Post post = postRepository.getByIds(postId);
        post.setTitle(postRequest.getTitle());
        post.setImage(postRequest.getImage());
        post.setDescription(postRequest.getDescription());
        post.setHometype(postRequest.getHometype());
        post.setPersons(postRequest.getPersons());
        post.setPrice(postRequest.getPrice());
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(" Удачно сохранился !")
                .build();
    }

    @Override
    public SimpleResponse delete(Long postId) {
        Post post = postRepository.getByIds(postId);
        postRepository.delete(post);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(" Удачно удален !")
                .build();
    }

    @Override
    public List<PostResponseAlls> allPost() {
        return postRepository.getAll();
    }

    @Override
    public PostResponseOne findPost(Long postId) {
        return postRepository.findPostId(postId);
    }

    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User current = userRepo.getByEmail(email);
        if (current.getRole().equals(Role.ADMIN))
            return current;
        else throw new AccessDeniedException("Forbidden 403");
    }
}
