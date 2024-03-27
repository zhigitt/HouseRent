package houserent.dto.response;

import houserent.entity.enums.Region;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class PostVendorAll {
    private Long id;
    private String name;
    private String email;
    private String title;
    private BigDecimal price;
    private String description;
    private Integer persons;
    private Double mark;
    private String city;
    private Region region;
    private String street;
    private LocalDate chekin;
    private LocalDate chekOut;

    public PostVendorAll(Long id, String name, String email, String title, BigDecimal price, String description, Integer persons, Double mark, String city, Region region, String street, LocalDate chekin, LocalDate chekOut) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.title = title;
        this.price = price;
        this.description = description;
        this.persons = persons;
        this.mark =  mark != null ? mark : 0.0;
        this.city = city;
        this.region = region;
        this.street = street;
        this.chekin = chekin;
        this.chekOut = chekOut;
    }

    private List<String> images;

}
