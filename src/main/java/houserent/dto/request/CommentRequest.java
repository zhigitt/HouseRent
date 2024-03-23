package houserent.dto.request;

import lombok.*;

import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentRequest {
    private String comment;
    private ZonedDateTime date;
    private String image;
    private int mark;
}
