package houserent.dto;

import java.time.ZonedDateTime;

public record CommentResponse(
        Long id,
        String comment,
        ZonedDateTime date,
        String image,
        int mark) {
}
