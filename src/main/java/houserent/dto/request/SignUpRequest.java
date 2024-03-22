package houserent.dto.request;

import houserent.entity.enums.Role;
import houserent.validation.EmailValidation;
import houserent.validation.PasswordValidation;
import houserent.validation.PhoneNumberValidation;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class SignUpRequest {
    private String name;

    @EmailValidation
    private String email;

    @PasswordValidation
    private String password;

    @PhoneNumberValidation
    private String phoneNumber;
    private Role role;
}
