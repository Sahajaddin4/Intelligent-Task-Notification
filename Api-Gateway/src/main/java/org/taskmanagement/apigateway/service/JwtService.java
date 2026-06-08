package org.taskmanagement.apigateway.service;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.taskmanagement.apigateway.exception.custom.TokenIsExpired;
import org.taskmanagement.apigateway.exception.custom.TokenIsInvalid;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {
    private final String SECRET_KEY ;
    public JwtService(@Value("${jwt.secret-key}") String secretKey) {
        this.SECRET_KEY = secretKey;
    }
    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    public String getUserName(String token){
        try {
            return extractClaims(token, Claims::getSubject);
        } catch (io.jsonwebtoken.ExpiredJwtException ex) {
            throw new TokenIsExpired("The provided token has expired. Please log in again.");
        } catch (io.jsonwebtoken.JwtException ex) {
            throw new TokenIsInvalid("Invalid token structure.");
        }
    }

    private <T> T extractClaims(String token, Function<Claims,T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }



    public boolean isTokenExpired(String token){
        Date expirationTime = extractClaims(token,Claims::getExpiration);
        return expirationTime.before(new Date());
    }
}

