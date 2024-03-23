package houserent.serivce;

import houserent.dto.request.CommentRequest;
import houserent.dto.response.CommentResponse;
import houserent.dto.SimpleResponse;

import java.util.List;

public interface CommentService {

    SimpleResponse save( Long postId,CommentRequest commentRequest);
    List<CommentResponse> getAllComment();
    List<CommentResponse> findByUserId(Long id);
    List<CommentResponse> findByPostId(Long id);
    SimpleResponse delete(Long id);
    SimpleResponse update(Long id,CommentRequest commentRequest);

}
