package houserent.dto.request;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
public class RentInfoRequest {
    private LocalDate chekin;
    private LocalDate chekOut;
}
