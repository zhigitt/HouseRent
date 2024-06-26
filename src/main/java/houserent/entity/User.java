package houserent.entity;

import houserent.entity.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.boot.model.internal.BinderHelper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "users")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", allocationSize = 1)
    private Long id;
    private String name;
    private String email;
    private BigDecimal card;
    private String password;
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToMany
    private List<Post> favoriteBasket;

    @OneToMany(cascade = CascadeType.REMOVE)
    private List<Post> booking;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<RentInfo> rentInfos;

    @OneToOne(cascade = CascadeType.REMOVE)
    private RentInfo rentInfo;

    public User(String name, String email, BigDecimal card, String password, String phoneNumber, Role role) {
        this.name = name;
        this.email = email;
        this.card = card;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}