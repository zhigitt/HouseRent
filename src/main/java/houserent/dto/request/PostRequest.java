package houserent.dto.request;

import houserent.entity.Address;
import houserent.entity.enums.HomeType;
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
    private List<String> image;
    private HomeType hometype;
    private BigDecimal price;
    private int persons;
    private Address address;
}
