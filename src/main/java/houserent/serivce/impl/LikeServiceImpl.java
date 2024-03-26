package houserent.serivce.impl;

import houserent.entity.Comment;
import houserent.entity.Like;
import houserent.entity.User;
import houserent.exception.NotFoundException;
import houserent.repository.CommentRepository;
import houserent.repository.PostRepository;
import houserent.repository.UserRepo;
import houserent.serivce.CommentService;
import houserent.serivce.LikeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class LikeServiceImpl implements LikeService {
    private final CommentRepository commentRepository;
    private final UserRepo userRepo;
    @Override @Transactional
    public void addLikes(Long commentId) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepo.getByEmail(email);
        Long userId = user.getId();
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new NotFoundException("With this id not found!"));

        if (!comment.getLike().getIsLike().contains(userId)){
            comment.getLike().getDisLike().remove(userId);
            comment.getLike().getIsLike().add(userId);
        }else {
            comment.getLike().getIsLike().remove(userId);
        }
    }

    @Override @Transactional
    public void disLikes(Long commentId) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepo.getByEmail(email);
        Long userId = user.getId();
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new NotFoundException("With this id not found!"));

        if(!comment.getLike().getDisLike().contains(userId)){
            comment.getLike().getIsLike().remove(userId);
            comment.getLike().getDisLike().add(userId);
        }else {
            comment.getLike().getDisLike().remove(userId);
        }
    }


}
