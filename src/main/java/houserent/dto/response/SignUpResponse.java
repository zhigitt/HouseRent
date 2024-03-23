package houserent.dto.response;

import houserent.dto.SimpleResponse;
import houserent.entity.enums.Role;
import lombok.Builder;
import org.springframework.http.HttpStatus;

@Builder
public record SignUpResponse(
        String token,
        HttpStatus httpStatus,
        String message
        ) {
}
