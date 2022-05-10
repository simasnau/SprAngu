package com.sprangu.spranguback.application.config;

import com.sprangu.spranguback.application.constants.CookieConstants;
import com.sprangu.spranguback.domain.user.model.security.UserDetailed;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtils {

    @Value("${SECRET}")
    String secret;

    public String generateToken(UserDetailed userDetailed) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CookieConstants.ID, userDetailed.getId());
        return createToken(claims, userDetailed);
    }

    private String createToken(Map<String, Object> claims, UserDetailed userDetailed) {
        return Jwts.builder()
                .setClaims(claims)
                .claim("ROLE", userDetailed.getAuthorities().toArray()[0])
                .setSubject(userDetailed.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 5000))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }
}
