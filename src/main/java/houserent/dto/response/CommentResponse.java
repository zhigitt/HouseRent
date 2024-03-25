package houserent.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class CommentResponse {
    private Long id;
    private String comment;
    private ZonedDateTime date;
    private List<String> images;
    private int mark;

    public CommentResponse(Long id, String comment, ZonedDateTime date, int mark) {
        this.id = id;
        this.comment = comment;
        this.date = date;
        this.mark = mark;
    }
}
