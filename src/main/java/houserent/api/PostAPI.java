package houserent.api;

import houserent.dto.request.PostRequest;
import houserent.dto.response.*;
import houserent.entity.enums.HomeType;
import houserent.entity.enums.Region;
import houserent.serivce.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post")
public class PostAPI {

    private final PostService postService;

    @Secured({"VENDOR","ADMIN"})
    @PostMapping("/save")
    public SimpleResponse savePost(@RequestBody PostRequest postRequest){
        return postService.save(postRequest);
    }
    @Secured({"VENDOR","ADMIN"})

    @PostMapping("/update/{postId}")
    public SimpleResponse update(@RequestBody PostRequest postRequest, @PathVariable Long postId){
        return postService.update(postId,postRequest);
    }
    @Secured({"VENDOR","ADMIN"})

    @PostMapping("/delete/{postId}")
    public SimpleResponse delete(@PathVariable Long postId){
        return postService.delete(postId);
    }
    @Secured({"VENDOR","ADMIN","CLIENT"})

    @GetMapping("/all")
    public List<PaginationPost> all (@RequestParam int page, @RequestParam int size){
        return postService.allPost(page,size);
    }
    @Secured({"VENDOR","ADMIN","CLIENT"})

    @GetMapping("/find/{postId}")
    public PostResponseOne find (@PathVariable Long postId){
        return postService.findPost(postId);
    }
    @Secured({"VENDOR","ADMIN","CLIENT"})
    @GetMapping("/search")
    public List<PostResponseAlls> search(@RequestParam String word){
        return postService.search(word);
    }
    @Secured({"VENDOR","ADMIN","CLIENT"})
    @GetMapping("/sort")
    public List<PostResponseAlls>sort(@RequestParam Region region){
        return postService.sort(region);
    }
    @Secured({"VENDOR","ADMIN","CLIENT"})
    @GetMapping("/filter")
    public List<PostResponseAlls>filter(@RequestParam HomeType homeType){
        return postService.filter(homeType);
    }

    @Secured({"VENDOR","ADMIN","CLIENT"})
    @GetMapping("/price")
    public List<PostResponseAlls> filterPrice(@RequestParam String word){
        return postService.priceFilter(word);
    }

    @Secured({"VENDOR","ADMIN"})

    @GetMapping("/vendor")
    public List<PostVendorAll> vendorProfile(){
        return postService.vendorAll();
    }

    @Secured({"VENDOR","ADMIN"})
    @GetMapping("/announcement")
    public List<PostAnnouncementAll> vendorAnnouncement(){
        return postService.announcementAll();
    }
    @Secured({"VENDOR","ADMIN"})
    @GetMapping("/favorite/{postId}")
    public FavoritePost favoritePost (@PathVariable Long postId){
        return postService.favoritePost(postId);
    }



}
