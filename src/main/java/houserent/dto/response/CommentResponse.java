package houserent.dto.response;

import java.time.ZonedDateTime;

public record CommentResponse(

        Long id,
        String comment,
        ZonedDateTime date,
        String image,
        double mark) {
}
