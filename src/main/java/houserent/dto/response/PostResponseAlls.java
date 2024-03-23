package houserent.dto.response;

import houserent.entity.Address;
import houserent.entity.enums.Region;
import jakarta.persistence.ElementCollection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PostResponseAlls
{
    @ElementCollection
    private String image;
    private String description;
    private Integer persons;
    private Integer mark;
    private String city;
    private Region region;
    private String street;
    private Boolean favorite;
    private Boolean book;
}
