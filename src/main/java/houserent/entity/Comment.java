package houserent.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.ZonedDateTime;
import java.util.ArrayList;
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
    @ElementCollection
    private List<String> images;
    private int mark;

    @ManyToOne
    private Post post;

    @OneToMany(mappedBy = "comment")
    private List<Like> likes;

    @ManyToOne
    private User user;
    @PrePersist
    private void prepersist(){
        this.date = ZonedDateTime.now();
    }
}
