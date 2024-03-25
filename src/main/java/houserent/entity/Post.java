package houserent.entity;

import houserent.entity.enums.HomeType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "posts")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "post_seq")
    @SequenceGenerator(name = "post_seq", allocationSize = 1)
    private Long id;
    private String title;
    private String description;
    @ElementCollection
    private String image;
    @Enumerated(EnumType.STRING)
    private HomeType hometype;
    private BigDecimal price;
    private int persons;
    private boolean favorite;
    private boolean book;

    @ManyToOne
    private User users;

    @OneToMany(mappedBy = "post")
    private List<Comment> comments;

    @OneToOne
    private Address address;

    @ManyToMany(mappedBy = "posts")
    private List<InFavorite> inFavorites;

}