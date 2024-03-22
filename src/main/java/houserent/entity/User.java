package houserent.entity;

import houserent.entity.enums.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", allocationSize = 1)
    private Long id;
    private String name;
    private String email;
    private String password;
    private String phoneNumber;

    @ManyToMany
    private List<Post> favoriteBasket;

    @OneToMany
    private List<Post> booking;
    private Role role;

    @OneToMany(mappedBy = "user")
    private List<RentInfo> rentInfos;

    @OneToOne
    private RentInfo rentInfo;
}