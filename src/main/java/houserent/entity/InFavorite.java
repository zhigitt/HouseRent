package houserent.entity;

import houserent.entity.Post;
import houserent.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.ZonedDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "in_favorites")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InFavorite {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "in_favorite_seq")
    @SequenceGenerator(name = "in_favorite_seq", allocationSize = 1)
    private Long id;
    private ZonedDateTime date;

    @OneToOne
    private User user;

    @ManyToMany
    private List<Post> posts;
}