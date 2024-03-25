package houserent.serivce.impl;

import houserent.dto.request.CommentRequest;
import houserent.dto.response.CommentResponse;
import houserent.dto.response.SimpleResponse;
import houserent.entity.Comment;
import houserent.entity.Like;
import houserent.entity.Post;
import houserent.entity.User;
import houserent.exception.NotFoundException;
import houserent.repository.CommentRepository;
import houserent.repository.PostRepository;
import houserent.repository.UserRepo;
import houserent.serivce.CommentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Not;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final UserRepo userRepo;
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Override @Transactional
    public SimpleResponse save(Long postId, CommentRequest commentRequest) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepo.getByEmail(email);

        Post post = postRepository.findById(postId).orElseThrow(() -> new NotFoundException("With this id not found!"));
        Comment comment = new Comment();
        comment.setComment(commentRequest.getComment());

        comment.setImages(commentRequest.getImage());
        comment.setMark(commentRequest.getMark());
        comment.setUser(user);
        comment.setPost(post);
        calculateAverageMark(postId);
        commentRepository.save(comment);

        Like like = new Like();
        like.setComment(comment);
        comment.setLike(like);
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
        return commentRepository.findCommentsByUserId(id);
    }

    @Override
    public List<CommentResponse> findByPostId(Long id) {

        List<CommentResponse> postComments = commentRepository.findCommentsByPostId(id);
        for (CommentResponse commentResponse : postComments) {
            Comment comment = commentRepository.findById(commentResponse.getId()).orElseThrow(() -> new NotFoundException(""));
            if (comment.getId().equals(commentResponse.getId())) {
                System.err.println("in if post id " + id + ", comment id " + comment.getId());
                System.err.println("comment images size " + comment.getImages().size());
                commentResponse.setImages(comment.getImages());
            }
        }

        return postComments;
    }

    @Override
    public SimpleResponse delete(Long id) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepo.getByEmail(email);
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new NotFoundException("With this id not found !"));
        if(!comment.getUser().equals(user)){
            throw new NotFoundException("Forbidden! ");
        }
        commentRepository.delete(comment);
//        commentRepository.deleteComWithPostId(id);
//        commentRepository.deleteComWithUserId(id);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Successfully deleted! ")
                .build();
    }

    @Override
    public SimpleResponse update(Long id, CommentRequest commentRequest) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepo.getByEmail(email);

        Comment comment = commentRepository.findById(id).orElseThrow(() -> new NotFoundException("With this id not found!"));
        Post post = postRepository.findById(id).orElseThrow(() -> new NotFoundException("With this id not found!"));

        for (Comment commentt : post.getComments()) {
            if (commentt.getUser().getId().equals(user.getId())) {
                comment.setComment(commentRequest.getComment());
                comment.setImages(commentRequest.getImage());
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

    public double calculateAverageMark(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new NotFoundException("Post not found with id: " + postId));
        List<Comment> comments = commentRepository.findByPost(post);

        if (comments.isEmpty()) {
            return 0; // или любое другое значение по умолчанию
        }

        int totalMark = comments.stream().mapToInt(Comment::getMark).sum();
        double averageMark = (double) totalMark / comments.size();

        // Ограничение средней оценки до 5
        return Math.min(5, averageMark);
    }


}
