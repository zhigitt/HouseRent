package houserent.dto.response;

import houserent.entity.Address;
import houserent.entity.enums.Region;
import jakarta.persistence.ElementCollection;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PostResponseAlls
{
    private String title;
    private String image;
    private BigDecimal price;
    private String description;
    private Integer persons;
    private Integer mark;
    private String city;
    private Region region;
    private String street;
    private Boolean favorite;
    private Boolean book;
}
