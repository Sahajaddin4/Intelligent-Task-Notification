package org.taskmanagement.authservice.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.taskmanagement.authservice.entity.Auth;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private  final String SECRET_KEY ;
    public JwtService(@Value("${jwt.secretKey}") String secretKey) {
        this.SECRET_KEY = secretKey;
    }
    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    public String getUserName(String token){
        return extractClaims(token, Claims::getSubject);
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
   public boolean validateToken(String token,Auth authDetails) {
          final String userName = getUserName(token);
            return userName.equals(authDetails.getEmail()) && isTokenExpired(token);
   }
    public  String generateToken(Auth authData) {
        Map<String, Long> claims = new HashMap<>();
        claims.put("id", authData.getId());
        return Jwts.builder()
                .claims(claims)
                .subject(authData.getEmail())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 15 * 60))
                .signWith(getSecretKey())
                .compact();
    }

    public boolean isTokenExpired(String token){
        Date expirationTime = extractClaims(token,Claims::getExpiration);
        return expirationTime.before(new Date());
    }
}
