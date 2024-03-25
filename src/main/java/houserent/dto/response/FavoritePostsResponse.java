package houserent.dto.response;

import houserent.entity.enums.Region;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder

public class FavoritePostsResponse {
    private Long id;
    private String title;
    private String image;
    private BigDecimal price;
}
