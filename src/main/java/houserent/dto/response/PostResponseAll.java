package houserent.dto.response;

import houserent.entity.Address;

import java.util.List;

public record PostResponseAll(

        List<String> image,
        String description,
        int persons,
        int mark,
        Address address,
        boolean favorite,
        boolean book
) {
}
