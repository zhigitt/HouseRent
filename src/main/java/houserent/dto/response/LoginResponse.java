package houserent.dto.response;

import houserent.entity.enums.Role;
import lombok.Builder;

@Builder
public record LoginResponse(
        String token,
        Long id,
        String email,
        Role role
) {
}
