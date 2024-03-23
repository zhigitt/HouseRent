package houserent.validation.validator;

import houserent.validation.CardValidation;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CardValidator implements ConstraintValidator<CardValidation, Integer> {
    @Override
    public boolean isValid(Integer card, ConstraintValidatorContext constraintValidatorContext) {
        if(card > 0){
            return true;
        }
        return false;
    }
}
