package houserent.serivce;

import houserent.dto.SimpleResponse;
import houserent.dto.request.PostRequest;
import houserent.dto.response.PostResponseAll;
import houserent.dto.response.PostResponseOne;

import java.util.List;

public interface PostService {
    SimpleResponse save(PostRequest postRequest);

    SimpleResponse update(Long postId, PostRequest postRequest);

    SimpleResponse delete(Long postId);

    List<PostResponseAll> allPost();

    PostResponseOne findPost(Long postId);
}
