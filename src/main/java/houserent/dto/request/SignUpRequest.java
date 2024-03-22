package houserent.dto.request;

import houserent.entity.enums.Role;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class SignUpRequest {
    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    private Role role;
}
