package houserent.validation.validator;

import houserent.validation.PasswordValidation;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<PasswordValidation, String> {
    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
        return password.length() <= 4;
    }
}
