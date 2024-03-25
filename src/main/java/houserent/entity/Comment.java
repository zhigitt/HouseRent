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
    private double mark;

    @ManyToOne
    private Post post;

    @OneToMany(mappedBy = "comment")
    private List<Like> likes;

    @ManyToOne
    private User user;

    public Comment(Long id, String comment, ZonedDateTime date) {
        this.id = id;
        this.comment = comment;
        this.date = date;
    }
}