package houserent.api;

import houserent.dto.request.PostRequest;
import houserent.dto.response.*;
import houserent.entity.enums.HomeType;
import houserent.entity.enums.Region;
import houserent.serivce.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post")
public class PostAPI {

    private final PostService postService;

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
    public List<PaginationPost> all (@RequestParam int page, @RequestParam int size){
        return postService.allPost(page,size);
    }
    @GetMapping("/find/{postId}")
    public PostResponseOne find (@PathVariable Long postId){
        return postService.findPost(postId);
    }

    @GetMapping("/search")
    public PostResponseAlls search(@RequestParam String word){
        return postService.search(word);
    }
    @GetMapping("/sort")
    public List<PostResponseAlls>sort(@RequestParam Region region){
        return postService.sort(region);
    }

    @GetMapping("/filter")
    public List<PostResponseAlls>filter(@RequestParam HomeType homeType){
        return postService.filter(homeType);
    }

    @GetMapping("/price")
    public List<PostResponseAlls> filterPrice(@RequestParam String word){
        return postService.priceFilter(word);
    }

    @GetMapping("/vendor")
    public List<PostVendorAll> vendorProfile(){
        return postService.vendorAll();
    }

    @GetMapping("/announcement")
    public List<PostAnnouncementAll> vendorAnnouncement(){
        return postService.announcementAll();
    }

//    @GetMapping("/favorite")
//    public FavoritePost favoritePost (){
//        return postService.favoritePost();
//    }



}
