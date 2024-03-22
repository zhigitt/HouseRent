package houserent.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import houserent.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;


@Service
public class JwtService {
    @Value("${app.jwt.secret}")

//    Salam R|TEst


    private String secretKey;

    public String createToken(User user){
        return JWT.create()
                .withClaim("email", user.getUsername())
                .withClaim("role", user.getRole().name())
                .withClaim("id", user.getId())
                .withIssuedAt(ZonedDateTime.now().toInstant())
                .withExpiresAt(ZonedDateTime.now().plusHours(1).toInstant())
                .sign(Algorithm.HMAC512(secretKey));
    }

    public String verifyToken(String token){
        JWTVerifier jwtVerify = JWT.require(Algorithm.HMAC512(secretKey)).build();
        DecodedJWT decodedJWT = jwtVerify.verify(token);
        return decodedJWT.getClaim("email").asString();
    }
}