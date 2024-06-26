package houserent.dto.request;

import houserent.entity.Address;
import houserent.entity.enums.HomeType;
import houserent.entity.enums.Region;
import jakarta.persistence.ElementCollection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.math.BigDecimal;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
public class PostRequest{



    private String title;
    private String description;
    @ElementCollection
    private List<String> images;
    private HomeType hometype;
    private BigDecimal price;
    private int persons;
    private Region region;
    private String city;
    private String street;
}
