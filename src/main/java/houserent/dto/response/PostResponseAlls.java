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
    private Long id;
    private String title;
    private BigDecimal price;
    private String description;
    private Integer persons;
    private Double mark;
    private String city;
    private Region region;
    private String street;
    private Boolean favorite;
    private Boolean book;
    private List<String> images;
    public PostResponseAlls(Long id,String title, BigDecimal price, String description, Integer persons, Double mark, String city, Region region, String street, Boolean favorite, Boolean book) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.description = description;
        this.persons = persons;
        this.mark = mark != null ? mark : 0.0;
        this.city = city;
        this.region = region;
        this.street = street;
        this.favorite = favorite;
        this.book = book;
    }


}
