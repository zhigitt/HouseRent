package houserent.dto.request;

import lombok.*;

import java.time.ZonedDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentRequest {
    private String comment;
    private ZonedDateTime date;
    private String image;
    private double mark;
    private List<String> image;
    private int mark;
}
