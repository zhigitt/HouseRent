package houserent.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "rent_infos")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RentInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rent_info_seq")
    @SequenceGenerator(name = "rent_info_seq", allocationSize = 1)
    private Long id;
    private LocalDate chekin;
    private LocalDate chekOut;

    public RentInfo(LocalDate chekin, LocalDate chekOut) {
        this.chekin = chekin;
        this.chekOut = chekOut;
    }

    @ManyToOne
    private User user;

    @OneToOne
    private Post post;
}