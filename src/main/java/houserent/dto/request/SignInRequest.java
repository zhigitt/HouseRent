package houserent.dto.request;

import houserent.validation.EmailValidation;
import houserent.validation.PasswordValidation;
import houserent.validation.UniqueEmailValidation;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class SignInRequest {
<<<<<<< HEAD
//    @UniqueEmailValidation
//    @EmailValidation
=======
    @EmailValidation
>>>>>>> 5719168338cf7fa2c762390a2f9896872b89d7e9
    private String email;

//    @PasswordValidation
    private String password;
}
