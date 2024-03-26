package houserent.api;


import houserent.dto.response.SimpleResponse;
import houserent.serivce.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/like")
public class LikeApi {

    private final LikeService likeService;

    @Secured("CLIENT")
    @PostMapping("/isLike/{commentId}")
    public SimpleResponse isLike(@PathVariable Long commentId){
        likeService.addLikes(commentId);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Like!")
                .build();
    }

    @Secured("CLIENT")
    @PostMapping("/disLike/{commentId}")
    public SimpleResponse disLike(@PathVariable Long commentId){
        likeService.disLikes(commentId);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("disLike!")
                .build();
    }
}
