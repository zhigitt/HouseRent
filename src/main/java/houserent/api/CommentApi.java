package houserent.api;

import houserent.dto.SimpleResponse;
import houserent.dto.request.CommentRequest;
import houserent.dto.response.CommentResponse;
import houserent.serivce.CommentService;
import jdk.dynalink.linker.support.SimpleLinkRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comment")
public class CommentApi{
    private final CommentService commentService;

    @Secured("CLIENT")
    @PostMapping("/save/{postId}")
    public SimpleResponse save(@PathVariable Long postId, @RequestBody CommentRequest commentRequest){
     commentService.save(postId,commentRequest);
     return SimpleResponse.builder()
             .httpStatus(HttpStatus.OK)
             .message("Successfully saved!")
             .build();
    }
    @Secured({"CLIENT","ADMIN"})
    @GetMapping("/getAll")
    public List<CommentResponse> getAll(){
       return commentService.getAllComment();
    }

    @Secured("CLIENT")
    @PostMapping("/findByUser/{userId}")
    public List<CommentResponse> getByUser(@PathVariable Long userId){
        return commentService.findByUserId(userId);
    }

    @Secured("CLIENT")
    @PostMapping("/findByPost/{postId}")
    public List<CommentResponse> getByPost(@PathVariable Long postId){
        return commentService.findByUserId(postId);
    }

    @Secured({"CLIENT","ADMIN"})
    @PostMapping("/delete/{postId}")
    public SimpleResponse delete(@PathVariable Long postId){
        commentService.delete(postId);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Successfully deleted!")
                .build();
    }

    @Secured("CLIENT")
    @PostMapping("/update/{postId}")
    public SimpleResponse update(@PathVariable Long postId,@RequestBody CommentRequest commentRequest){
        commentService.update(postId,commentRequest);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Successfully updated!")
                .build();
    }
}
