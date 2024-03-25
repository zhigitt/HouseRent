package houserent.dto.response;

import java.time.ZonedDateTime;

public record CommentResponse(
        String name,
        Long id,
        String comment,
        ZonedDateTime date,
        String image,
        int mark) {
}
