package houserent.dto.response;

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
public class PaginationPost{
    private int page;
    private int size;
    private String title;
    private List<String> images;
    private BigDecimal price;
    private String description;
    private Integer persons;
    private double mark;
    private String city;
    private Region region;
    private String street;
    private Boolean favorite;
    private Boolean book;
}
