package houserent.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "likes")
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "like_seq")
    @SequenceGenerator(name = "like_seq", allocationSize = 1)
    private Long id;
    private boolean isLike;
    private boolean disLike;

    @ManyToOne
    private Comment comment;

    @OneToOne
    private User user;
}