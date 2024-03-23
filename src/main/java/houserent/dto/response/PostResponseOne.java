package houserent.dto.response;

import houserent.entity.Address;
import houserent.entity.Comment;
import houserent.entity.RentInfo;
import houserent.entity.enums.HomeType;
import houserent.entity.enums.Region;

import java.util.List;

public record PostResponseOne(

        String title,
        String image,
        HomeType hometype,
        int persons,
        String city,
        Region region,
        String street,
        String description,
        String name,
        String email,
        RentInfo rentInfo,
        boolean favorite,
        boolean book,
        Comment comment

) {
}
