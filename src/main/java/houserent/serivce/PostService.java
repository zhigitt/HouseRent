package houserent.serivce;

<<<<<<< HEAD

=======
>>>>>>> 6db82be01c2a9595fc3a42e5720370c04306c800
import houserent.dto.request.PostRequest;
import houserent.dto.response.*;
import houserent.entity.enums.HomeType;
import houserent.entity.enums.Region;

import java.util.List;

public interface PostService {
    SimpleResponse save(PostRequest postRequest);

    SimpleResponse update(Long postId, PostRequest postRequest);

    SimpleResponse delete(Long postId);

    List<PaginationPost> allPost(int page, int size);

    PostResponseOne findPost(Long postId);

    PostResponseAlls search(String word);

    List<PostResponseAlls> sort(Region region);

    List<PostResponseAlls> filter(HomeType homeType);

    List<PostResponseAlls> priceFilter(String word);

    List<PostVendorAll> vendorAll();

    List<PostAnnouncementAll> announcementAll();
}
