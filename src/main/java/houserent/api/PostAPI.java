package houserent.api;

import houserent.dto.request.PostRequest;
import houserent.dto.response.PostResponseAll;
import houserent.dto.response.PostResponseOne;
import houserent.dto.response.SimpleResponse;
import houserent.serivce.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post")
public class PostAPI {

    private final PostService postService;

//    ???????
    @PostMapping("/save")
    public SimpleResponse savePost(@RequestBody PostRequest postRequest){
        return postService.save(postRequest);
    }

    @PostMapping("/update/{postId}")
    public SimpleResponse update(@RequestBody PostRequest postRequest, @PathVariable Long postId){
        return postService.update(postId,postRequest);
    }

    @PostMapping("/delete/{postId}")
    public SimpleResponse delete(@PathVariable Long postId){
        return postService.delete(postId);
    }

    @GetMapping("/all")
    public List<PostResponseAll> all (){
        return postService.allPost();
    }
    @GetMapping("/find/{postId}")
    public PostResponseOne find (@PathVariable Long postId){
        return postService.findPost(postId);
    }


}
