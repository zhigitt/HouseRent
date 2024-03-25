package houserent.dto.request;

import houserent.validation.CardValidation;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ReplenishRequest {
    @CardValidation
    private BigDecimal card;
}
