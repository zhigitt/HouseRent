package houserent.dto.response;

import houserent.entity.enums.Region;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostVendorAll {
    private String name;
    private String email;
    private String title;
    private String image;
    private BigDecimal price;
    private String description;
    private Integer persons;
    private Integer mark;
    private String city;
    private Region region;
    private String street;
    private LocalDate chekin;
    private LocalDate chekOut;
}
