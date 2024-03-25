package houserent.dto.request;

import houserent.entity.enums.Role;
import houserent.validation.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter @Getter
public class SignUpRequest {
    private String name;

    @EmailValidation
//    @UniqueEmailValidation
    private String email;

    @PasswordValidation
    private String password;

    @CardValidation
    private BigDecimal card;
    @PhoneNumberValidation
    private String phoneNumber;
    private Role role;
}
