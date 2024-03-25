package houserent.serivce.impl;

import houserent.dto.request.CommentRequest;
import houserent.dto.response.CommentResponse;
import houserent.dto.response.SimpleResponse;
import houserent.entity.Comment;
import houserent.entity.Post;
import houserent.entity.User;
import houserent.exception.NotFoundException;
import houserent.repository.CommentRepository;
import houserent.repository.PostRepository;
import houserent.repository.UserRepo;
import houserent.serivce.CommentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final UserRepo userRepo;
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Override @Transactional
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

        double rating = announcementRating(post.getComments());
        double roundedRating = Math.round(rating * 10.0) / 10.0;
        double limitedRating = Math.min(roundedRating, 5.0);
        post.setMark(limitedRating);

        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Successfully saved! ")
                .build();
    }

    private double announcementRating(List<Comment> feedbacks) {
        if (feedbacks.isEmpty()) {
            return 0;
        }

        double sumRatings = 0;
        for (Comment feedback : feedbacks) {
            sumRatings += feedback.getMark();
        }

        double averageRating = sumRatings / feedbacks.size();

        return averageRating * (5.0 / getMaxRating(feedbacks));
    }
    private double getMaxRating(List<Comment> feedbacks) {
        double maxRating = Double.MIN_VALUE;
        for (Comment feedback : feedbacks) {
            if (feedback.getMark() > maxRating) {
                maxRating = feedback.getMark();
            }
        }
        return maxRating;
    }

    @Override
    public List<CommentResponse> getAllComment() {
       return commentRepository.getAll();

    }

    @Override
    public List<CommentResponse> findByUserId(Long id) {
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
