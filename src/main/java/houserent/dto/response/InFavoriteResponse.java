package houserent.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
@AllArgsConstructor
public class InFavoriteResponse {

    private String name;
    private Long id;
    private String userName;
    private ZonedDateTime date;
}
