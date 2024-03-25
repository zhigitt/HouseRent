package houserent.serivce.impl;

import houserent.dto.SimpleResponse;
import houserent.dto.request.PostRequest;
import houserent.dto.response.PaginationPost;
import houserent.dto.response.PostResponseAlls;
import houserent.dto.response.PostResponseOne;
import houserent.dto.response.*;
import houserent.entity.Address;
import houserent.entity.Comment;
import houserent.entity.Post;
import houserent.entity.User;
import houserent.entity.enums.HomeType;
import houserent.entity.enums.Region;
import houserent.entity.enums.Role;
import houserent.repository.AddressRepository;
import houserent.repository.PostRepository;
import houserent.repository.UserRepo;
import houserent.serivce.PostService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
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
        getCurrentUser();
        Post post = postRepository.getByIds(postId);
        postRepository.delete(post);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(" Удачно удален !")
                .build();
    }

    @Override
    public List<PaginationPost> allPost(int page, int size) {

        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Post> posts = postRepository.getAllPage(pageable);
        List<PaginationPost> responseAlls = new ArrayList<>();
        for (Post post : posts.getContent()) {
                PaginationPost postResponseAlls = PaginationPost.builder()
                        .page(posts.getNumber() + 1)
                        .size(posts.getTotalPages())
                        .title(post.getTitle())
                        .image(post.getImage())
                        .price(post.getPrice())
                        .description(post.getDescription())
                        .persons(post.getPersons())
                        .region(post.getAddress().getRegion())
                        .city(post.getAddress().getCity())
                        .street(post.getAddress().getStreet())
                        .favorite(post.isFavorite())
                        .book(post.isBook())
                        .build();
                responseAlls.add(postResponseAlls);
            }
        return responseAlls;
    }

    @Override
    public PostResponseOne findPost(Long postId) {
        return postRepository.findPostId(postId);
    }

    @Override
    public PostResponseAlls search(String word) {
        return postRepository.search(word);
    }

    @Override
    public List<PostResponseAlls> sort(Region region) {
        return postRepository.sortReqion(region);
    }

    @Override
    public List<PostResponseAlls> filter(HomeType homeType) {

        if (homeType.equals(HomeType.HOUSE) || homeType.equals(HomeType.APARTMENT)  || homeType.equals(HomeType.ALL))  {
            return postRepository.filterHouseAndApartment(homeType);
        }
        return postRepository.getAll();
    }

    @Override
    public List<PostResponseAlls> priceFilter(String word) {
        return postRepository.priceFilter(word);
    }

    @Override
    public List<PostVendorAll> vendorAll() {
        getCurrentUser();
        return postRepository.vendorAllPost();
    }

    @Override
    public List<PostAnnouncementAll> announcementAll() {
        getCurrentUser();
        return postRepository.announcement();
    }

    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User current = userRepo.getByEmail(email);
        if (current.getRole().equals(Role.ADMIN)|| current.getRole().equals(Role.VENDOR))
            return current;
        else throw new AccessDeniedException("Forbidden 403");
    }
}
