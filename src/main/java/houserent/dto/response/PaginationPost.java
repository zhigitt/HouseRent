package houserent.dto.response;

import houserent.entity.enums.Region;
import lombok.*;

import java.math.BigDecimal;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PaginationPost{
    private int page;
    private int size;
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
