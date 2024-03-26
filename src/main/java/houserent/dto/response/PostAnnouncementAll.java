package houserent.dto.response;

import houserent.entity.enums.Region;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostAnnouncementAll {
    private Long id;

    public PostAnnouncementAll(Long id, String name, String email, String title, BigDecimal price, String description, Integer persons, double mark, String city, Region region, String street) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.title = title;
        this.price = price;
        this.description = description;
        this.persons = persons;
        this.mark = mark;
        this.city = city;
        this.region = region;
        this.street = street;
    }

    private String name;
    private String email;
    private String title;
    private BigDecimal price;
    private String description;
    private Integer persons;
    private double mark;
    private String city;
    private Region region;
    private String street;
    private List<String> images;

}
