package houserent.serivce.impl;

import houserent.dto.CommentRequest;
import houserent.dto.CommentResponse;
import houserent.dto.SimpleResponse;
import houserent.entity.Comment;
import houserent.entity.Post;
import houserent.entity.User;
import houserent.exception.NotFoundException;
import houserent.repository.CommentRepository;
import houserent.repository.PostRepository;
import houserent.repository.UserRepo;
import houserent.serivce.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final UserRepo userRepo;
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Override
    public SimpleResponse save(Long postId,CommentRequest commentRequest) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepo.getByEmail(email);
        Post post = postRepository.findById(postId).orElseThrow(() -> new NotFoundException("With this id not found!"));
        Comment comment = new Comment();
        comment.setComment(commentRequest.getComment());
        comment.setDate(commentRequest.getDate());
        comment.setImage(commentRequest.getImage());
        comment.setMark(commentRequest.getMark());
        comment.setUser(user);
        comment.setPost(post);
        commentRepository.save(comment);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Successfully saved! ")
                .build();
    }

    @Override
    public List<CommentResponse> getAllComment() {
       return commentRepository.getAll();

    }

    @Override
    public List<CommentResponse> findByUserId(Long id) {
//        User user = userRepo.findById(id).orElseThrow(() -> new NotFoundException("With this id not found! "));
        return commentRepository.findCommentsByUserId(id);
    }

    @Override
    public List<CommentResponse> findByPostId(Long id) {
        return commentRepository.findCommentsByPostId(id);
    }

    @Override
    public SimpleResponse delete(Long id) {

      commentRepository.deleteComWithUserId(id);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Successfully deleted! ")
                .build();
    }

    @Override
    public SimpleResponse update(Long id,CommentRequest commentRequest) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepo.getByEmail(email);

        Comment comment = commentRepository.findById(id).orElseThrow(() -> new NotFoundException("With this id not found!"));
        Post post = postRepository.findById(id).orElseThrow(() -> new NotFoundException("With this id not found!"));

        for (Comment commentt : post.getComments()) {
             if(commentt.getUser().getId() == user.getId()){
                 comment.setComment(commentRequest.getComment());
                 comment.setDate(commentRequest.getDate());
                 comment.setImage(commentRequest.getImage());
                 comment.setMark(commentRequest.getMark());
                 comment.setUser(user);
                 commentRepository.save(comment);
             }
        }

        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Successfully updated! ")
                .build();
    }


}
