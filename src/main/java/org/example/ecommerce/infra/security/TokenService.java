package org.example.ecommerce.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.example.ecommerce.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.jwt.public.key}")
    RSAPublicKey rsaPublicKey;

    @Value("${api.jwt.private.key}")
    RSAPrivateKey rsaPrivateKey;


    public String generateToken(User user) {
        try {
            Algorithm algorithm = Algorithm.RSA256(rsaPublicKey, rsaPrivateKey);
            String token = JWT.create()
                    .withIssuer("ecommerce")
                    .withSubject(user.getUsername())
                    .withExpiresAt(ExpiresAt())
                    .sign(algorithm);
            return token;
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Token not created.");
        }
    }

    public String validateToken(String token) {

        try {
            Algorithm algorithm = Algorithm.RSA256(rsaPublicKey, rsaPrivateKey);
            return JWT.require(algorithm)
                    .withIssuer("ecommerce")
                    .build()
                    .verify(token)
                    .getSubject();
            } catch (JWTVerificationException jwtVerificationException) {
            throw new RuntimeException("");
        }
    }

    private Instant ExpiresAt() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
