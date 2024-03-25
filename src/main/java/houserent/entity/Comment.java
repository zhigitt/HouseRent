package houserent.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.ZonedDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "comments")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comment_seq")
    @SequenceGenerator(name = "comment_seq", allocationSize = 1)
    private Long id;
    private String comment;
    private ZonedDateTime date;
    private String image;
    private int mark;

    @ManyToOne
    private Post post;

    @OneToOne(mappedBy = "comment")
    private Like like;

    @ManyToOne
    private User user;

    public Comment(Long id, String comment, ZonedDateTime date) {
        this.id = id;
        this.comment = comment;
        this.date = date;
    }
}