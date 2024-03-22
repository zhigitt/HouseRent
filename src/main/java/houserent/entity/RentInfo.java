package houserent.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "rent_info")
public class RentInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rent_info_seq")
    @SequenceGenerator(name = "rent_info_seq", allocationSize = 1)
    private Long id;
    private LocalDate chekin;
    private LocalDate chekOut;

    @ManyToOne
    private User user;

    @OneToOne
    private Post post;
}