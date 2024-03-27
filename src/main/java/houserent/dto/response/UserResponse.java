package houserent.dto.response;

import houserent.entity.enums.Role;
import lombok.*;
import org.springframework.instrument.classloading.ResourceOverridingShadowingClassLoader;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {
    private Long id;
    private String name;
    private Role role;
    private String email;
}
