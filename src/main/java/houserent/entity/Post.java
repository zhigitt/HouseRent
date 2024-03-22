package houserent.entity;

import houserent.entity.enums.HomeType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "post_seq")
    @SequenceGenerator(name = "post_seq", allocationSize = 1)
    private Long id;
    private String title;
    private String description;
    private String image;
    private HomeType hometype;
    private int persons;
    private boolean favorite;

    @ManyToOne
    private User users;

    @OneToMany(mappedBy = "post")
    private List<Comment> comments;

    @OneToOne
    private Address address;

    @ManyToMany
    private List<InFavorite> inFavorites;

}