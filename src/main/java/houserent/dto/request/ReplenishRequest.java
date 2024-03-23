package houserent.dto.request;

import houserent.validation.CardValidation;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReplenishRequest {
    @CardValidation
    private int card;
}
