package houserent.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "comments")
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

    @OneToMany(mappedBy = "comment")
    private List<Like> likes;

    @ManyToOne
    private User user;
}