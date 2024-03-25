package houserent.dto.response;

import houserent.entity.enums.HomeType;
import houserent.entity.enums.Region;

public record FavoritePost (String title,
                            String image,
                            HomeType hometype,
                            int persons,
                            String city,
                            Region region,
                            String street,
                            String description){


}
