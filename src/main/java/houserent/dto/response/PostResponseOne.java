package houserent.dto.response;

import houserent.entity.Address;
import houserent.entity.Comment;
import houserent.entity.RentInfo;
import houserent.entity.enums.HomeType;

import java.util.List;

public record PostResponseOne(

        String title,
        List<String> image,
        HomeType hometype,
        int persons,
        Address address,
        String description,
        String name,
        String email,
        RentInfo rentInfo,
        boolean favorite,
        boolean book,
        Comment comment

) {
}
