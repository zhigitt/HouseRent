package houserent.dto.request;

import houserent.validation.EmailValidation;
import houserent.validation.PasswordValidation;
import houserent.validation.UniqueEmailValidation;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class SignInRequest {
//    @UniqueEmailValidation
//    @EmailValidation
    private String email;

//    @PasswordValidation
    private String password;
}
