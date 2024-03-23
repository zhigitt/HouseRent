package houserent.serivce;

import houserent.dto.request.PostRequest;
import houserent.dto.response.PostResponseAlls;
import houserent.dto.response.PostResponseOne;
import houserent.dto.response.SimpleResponse;

import java.util.List;

public interface PostService {
    SimpleResponse save(PostRequest postRequest);

    SimpleResponse update(Long postId, PostRequest postRequest);

    SimpleResponse delete(Long postId);

    List<PostResponseAlls> allPost();

    PostResponseOne findPost(Long postId);
}
