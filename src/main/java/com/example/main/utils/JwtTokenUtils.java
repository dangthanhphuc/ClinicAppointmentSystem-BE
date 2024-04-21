package com.example.main.utils;

import com.example.main.entities.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoder;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenUtils {

    @Value("${jwt.expiration}")
    private int expiration;
    
    @Value("${jwt.secret-key}")
    private String secretKey;

    private SecretKey getSignInKey(){
        // Tạo SignInKey
        String secretKeyEncode = Encoders.BASE64.encode(secretKey.getBytes());
        byte[] secretKeyDecode = Decoders.BASE64.decode(secretKeyEncode);

        return Keys.hmacShaKeyFor(secretKeyDecode);
    }

    public String generateToken(User user){
        // Tạo Claims
        Map<String, String> claims = new HashMap<>();
        claims.put("username", user.getUsername());

        return Jwts.builder()
                .claims(claims)
                .signWith(getSignInKey()) // Signature Key
                .issuer("https://localhost:8088/api/v1/") //Issuer cung cấp thông tin về địa chỉ (URL hoặc chuỗi định danh) của đơn vị tạo ra JWT. Nhằm xác định nguồn gốc của JWT và có thể hữu ích trong việc xác thực và kiểm tra tính toàn vẹn của JWT.
                .subject(user.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()* expiration * 1000L))
                .compact();
    }

    private Claims extractAllClaimsFromToken (String token) {
        return Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private <T> T extractClaimFromToken(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }


    public String getUsernameFromToken (String token) {
        return extractClaimFromToken(token, Claims::getSubject);
    }

    public boolean isTokenExpired (String token) {
        return extractClaimFromToken(token, Claims::getExpiration).before(new Date());
    }

    public boolean validateToken(String token, User userDetails) {
        try {
            boolean isExpired = isTokenExpired(token);
            String username = getUsernameFromToken(token);
            return username.equals(userDetails.getUsername()) && !isExpired ;

        } catch (
        MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (
        ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (
        UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }

}
