package houserent.dto.response;

import lombok.Builder;
import org.springframework.http.HttpStatus;

@Builder
public record SignUpResponse(
        String token,
        HttpStatus httpStatus,
        String message
        ) {
}
