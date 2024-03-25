package houserent.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.w3c.dom.Comment;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "likes")
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "like_seq")
    @SequenceGenerator(name = "like_seq", allocationSize = 1)
    private Long id;
    @ElementCollection
    private List<Long> isLike = new ArrayList<>();
    @ElementCollection
    private List<Long> disLike = new ArrayList<>();

    @OneToOne
    private Comment comment;

    @OneToOne
    private User user;
}