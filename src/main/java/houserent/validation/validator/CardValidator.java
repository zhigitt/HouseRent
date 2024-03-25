package houserent.validation.validator;

import houserent.validation.CardValidation;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.math.BigDecimal;

public class CardValidator implements ConstraintValidator<CardValidation, BigDecimal> {
    @Override
    public boolean isValid(BigDecimal card, ConstraintValidatorContext constraintValidatorContext) {
        if(card.compareTo(BigDecimal.ZERO) > 0){
            return true;
        }
        return false;
    }
}
