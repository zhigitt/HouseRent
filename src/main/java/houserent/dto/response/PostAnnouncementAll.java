package houserent.dto.response;

import houserent.entity.enums.Region;
import lombok.*;

import java.math.BigDecimal;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostAnnouncementAll {
    private String name;
    private String email;
    private String title;
    private String image;
    private BigDecimal price;
    private String description;
    private Integer persons;
    private double mark;
    private String city;
    private Region region;
    private String street;
}
