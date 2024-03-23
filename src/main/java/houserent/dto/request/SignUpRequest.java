package houserent.dto.request;

import houserent.entity.enums.Role;
import houserent.validation.EmailValidation;
import houserent.validation.PasswordValidation;
import houserent.validation.PhoneNumberValidation;
import houserent.validation.UniqueEmailValidation;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class SignUpRequest {
    private String name;

    @EmailValidation
    @UniqueEmailValidation
    private String email;

    @PasswordValidation
    private String password;

    private int card;

    @PhoneNumberValidation
    private String phoneNumber;
    private Role role;
}
