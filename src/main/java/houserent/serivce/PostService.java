package houserent.serivce;

import houserent.dto.request.PostRequest;
import houserent.dto.response.*;
import houserent.entity.enums.HomeType;
import houserent.entity.enums.Region;

import java.util.List;

public interface PostService {
    SimpleResponse save(PostRequest postRequest);

    SimpleResponse update(Long postId, PostRequest postRequest);

    SimpleResponse delete(Long postId);

    List<PaginationPost> allPost(int page, int size);

    PostResponseOne findPost(Long postId);

    List<PostResponseAlls> search(String word);

    List<PostResponseAlls> sort(Region region);

    List<PostResponseAlls> filter(HomeType homeType);

    List<PostResponseAlls> priceFilter(String word);

    PostVendorAll vendorAll(Long postId);

    List<PostAnnouncementAll> announcementAll();

    FavoritePost favoritePost(Long postId);
}
