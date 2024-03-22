package houserent.validation.validator;

import houserent.repository.UserRepo;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmailValidator, String> {

    @Autowired
    private UserRepo userRepo;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        if (email == null){
            return true;
        }
        return !userRepo.findByEmail(email).isPresent();
    }

    @Override
    public void initialize(UniqueEmailValidator constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }
}
