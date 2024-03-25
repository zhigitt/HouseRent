package houserent.serivce;

import houserent.dto.request.PostRequest;
import houserent.dto.response.PaginationPost;
import houserent.dto.response.PostResponseAlls;
import houserent.dto.response.PostResponseOne;
import houserent.dto.response.SimpleResponse;
import houserent.entity.enums.HomeType;
import houserent.entity.enums.Region;

import java.util.List;

public interface PostService {
    SimpleResponse save(PostRequest postRequest);

    SimpleResponse update(Long postId, PostRequest postRequest);

    SimpleResponse delete(Long postId);

    List<PaginationPost> allPost(int page, int size);

    PostResponseOne findPost(Long postId);

    PostResponseAlls search(String word);

    List<PostResponseAlls> sort(Region region);

    List<PostResponseAlls> filter(HomeType homeType);

    List<PostResponseAlls> priceFilter(String word);
}
