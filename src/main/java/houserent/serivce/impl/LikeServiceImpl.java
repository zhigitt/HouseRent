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
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class LikeServiceImpl implements LikeService {
    private final CommentRepository commentRepository;
    private final UserRepo userRepo;
    @Override
    public void addLikes(Long commentId) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepo.getByEmail(email);
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new NotFoundException("With this id not found!"));




    }
}
