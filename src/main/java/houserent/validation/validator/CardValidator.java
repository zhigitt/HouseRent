package houserent.validation.validator;

import houserent.validation.CardValidation;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CardValidator implements ConstraintValidator<CardValidation, String> {
    @Override
    public boolean isValid(String card, ConstraintValidatorContext constraintValidatorContext) {
        return card != null;
    }
}
