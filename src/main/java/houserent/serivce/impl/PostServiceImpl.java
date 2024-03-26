package houserent.serivce.impl;

import houserent.dto.request.PostRequest;
import houserent.dto.response.*;
import houserent.entity.*;
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

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final UserRepo userRepo;
    private final AddressRepository addressRepository;

    @Override @Transactional
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
        post.setHometype(postRequest.getHometype());
        post.setPrice(postRequest.getPrice());
        post.setPersons(postRequest.getPersons());
        post.setImages(postRequest.getImages()); // Установка изображений

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
        getCurrentUser();
        Post post = postRepository.getByIds(postId);
        post.setTitle(postRequest.getTitle());
        post.setImages(postRequest.getImages());
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

        getCurrentUserClient();
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Post> posts = postRepository.getAllPage(pageable);
        List<PaginationPost> responseAlls = new ArrayList<>();
        for (Post post : posts.getContent()) {
                PaginationPost postResponseAlls = PaginationPost.builder()
                        .page(posts.getNumber() + 1)
                        .size(posts.getTotalPages())
                        .title(post.getTitle())
                        .images(post.getImages())
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
        getCurrentUserClient();
        Post post = postRepository.findPostId(postId);
        List<Comment> comments = post.getComments();
        List<CommentResponse> commentResponses = mapToCommentResponse(comments);
        return mapToPostResponse(post, commentResponses);
    }
    private PostResponseOne mapToPostResponse(Post post, List<CommentResponse> commentResponses) {
        return new PostResponseOne(
                post.getTitle(),
                post.getImages(),
                post.getHometype(),
                post.getPersons(),
                post.getMark(),
                post.getAddress().getCity(),
                post.getAddress().getRegion(),
                post.getAddress().getStreet(),
                post.getDescription(),
                post.getUsers().getName(),
                post.getUsers().getEmail(),
                post.getUsers().getRentInfo(),
                post.isFavorite(),
                post.isBook(),
                commentResponses
        );
    }

    private List<CommentResponse> mapToCommentResponse(List<Comment> comments) {
        List<CommentResponse> commentResponses = new ArrayList<>();
        for (Comment comment : comments) {
            // Создаем объект CommentResponse и заполняем его данными из Comment
            CommentResponse commentResponse = new CommentResponse(
                    comment.getId(),
                    comment.getComment(),
                    comment.getDate(),
                    comment.getImages(),
                    comment.getMark()
            );
            commentResponses.add(commentResponse);
        }
        return commentResponses;
    }

    @Override
    public List<PostResponseAlls> search(String word) {
        getCurrentUserClient();
       List<PostResponseAlls> search = postRepository.search(word);
        for (PostResponseAlls postResponseAlls : search) {
            postResponseAlls.setImages(postRepository.findImage(postResponseAlls.getId()));
        }
        return search;
    }

    @Override
    public List<PostResponseAlls> sort(Region region) {
        getCurrentUserClient();
        List<PostResponseAlls> postResponseAlls = postRepository.sortReqion(region);
        for (PostResponseAlls postResponseAll : postResponseAlls) {
            postResponseAll.setImages(postRepository.findImage(postResponseAll.getId()));
        }

        return postResponseAlls;
    }

    @Override
    public List<PostResponseAlls> filter(HomeType homeType) {
getCurrentUserClient();
        if (homeType.equals(HomeType.HOUSE) || homeType.equals(HomeType.APARTMENT)  || homeType.equals(HomeType.ALL))  {
            List<PostResponseAlls> postResponseAlls = postRepository.filterHouseAndApartment(homeType);
            for (PostResponseAlls postResponseAll : postResponseAlls) {
                postResponseAll.setImages(postRepository.findImage(postResponseAll.getId()));
            }
            return postResponseAlls;
        }
        return null;
    }

    @Override
    public List<PostResponseAlls> priceFilter(String word) {
        getCurrentUserClient();
        return postRepository.priceFilter(word);
    }

    @Override
    public List<PostVendorAll> vendorAll() {
        getCurrentUser();
        List<PostVendorAll> postVendorAlls = postRepository.vendorAllPost();
        for (PostVendorAll postVendorAll : postVendorAlls) {
            postVendorAll.setImages(postRepository.findImage(postVendorAll.getId()));
        }
        return postVendorAlls;
    }

    @Override
    public List<PostAnnouncementAll> announcementAll() {
        getCurrentUser();
        List<PostAnnouncementAll> announcement = postRepository.announcement();
        for (PostAnnouncementAll postAnnouncementAll : announcement) {
            postAnnouncementAll.setImages(postRepository.findImage(postAnnouncementAll.getId()));
        }
        return announcement;
    }

    @Override
    public FavoritePost favoritePost(Long postId) {
        getCurrentUserClient();
        FavoritePost favoritePost = postRepository.favoriteVendor(postId);

        Post post = postRepository.getByIds(favoritePost.getId());
        List<InFavoriteResponse> inFavoriteResponses = new ArrayList<>();
        for (InFavorite inFavorite : post.getInFavorites()) {
            inFavoriteResponses.add(new InFavoriteResponse(inFavorite.getUser().getName(),inFavorite.getId(), inFavorite.getUser().getName(), inFavorite.getDate()));
        }

        List<CommentResponse> commentResponses = new ArrayList<>();
        for (Comment comment : post.getComments()) {
            List<String> commentImage = new ArrayList<>();
            for (String image : comment.getImages()) {
                commentImage.add(image);
            }
                commentResponses.add(new CommentResponse(comment.getId(), comment.getComment(),comment.getDate(),commentImage, comment.getMark()));
                comment.setImages(comment.getImages());
        }


        favoritePost.setInFavorites(inFavoriteResponses);
        favoritePost.setComments(commentResponses);
        return favoritePost;
    }

    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User current = userRepo.getByEmail(email);
        if (current.getRole().equals(Role.ADMIN)|| current.getRole().equals(Role.VENDOR))
            return current;
        else throw new AccessDeniedException("Forbidden 403");
    }

    private User getCurrentUserClient() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User current = userRepo.getByEmail(email);
        if (current.getRole().equals(Role.ADMIN)|| current.getRole().equals(Role.VENDOR)||current.getRole().equals(Role.CLIENT))
            return current;
        else throw new AccessDeniedException("Forbidden 403");
    }
}
