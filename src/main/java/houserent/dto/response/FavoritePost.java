package houserent.dto.response;

import houserent.entity.Comment;
import houserent.entity.InFavorite;
import houserent.entity.enums.HomeType;
import houserent.entity.enums.Region;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class FavoritePost {
    private Long id;
    private String title;
    private String image;
    private HomeType hometype;
    private int persons;
    private String city;
    private Region region;
    private String street;
    private String description;
    private List<InFavoriteResponse> inFavorites;
    private List<CommentResponse> comments;

    public FavoritePost(Long id, String title, String image, HomeType hometype, int persons, String city, Region region, String street, String description) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.hometype = hometype;
        this.persons = persons;
        this.city = city;
        this.region = region;
        this.street = street;
        this.description = description;
    }
}
//    public FavoritePost(Long id, String title, String image, HomeType hometype, int persons, String city, Region region, String street, String description) {
//        this(id, title, image, hometype, persons, city, region, street, description, null, null);
//    }

