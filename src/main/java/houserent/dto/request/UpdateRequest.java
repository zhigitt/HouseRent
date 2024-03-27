package houserent.dto.request;

import houserent.entity.enums.Role;
import houserent.validation.CardValidation;
import houserent.validation.EmailValidation;
import houserent.validation.PasswordValidation;
import houserent.validation.PhoneNumberValidation;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class UpdateRequest {
    private String name;

    @PasswordValidation
    private String password;

    @PhoneNumberValidation
    private String phoneNumber;
}
