package br.com.diegosantos.minierp.identity.infrastructure.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Service
public class JwtService {

    @Value("${security.jwt.secret}")
    private String jwtSecret;

    @Value("${security.jwt.expiration}")
    private long jwtExpirationInSeconds;

    public String generateToken(String subject) {
        Instant now = Instant.now();
        Instant expiration = now.plusSeconds(jwtExpirationInSeconds);

        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(expiration))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    public OffsetDateTime extractExpiration(String token) {
        Date expiration = extractAllClaims(token).getExpiration();
        return OffsetDateTime.ofInstant(expiration.toInstant(), ZoneOffset.UTC);
    }

    public boolean isTokenValid(String token, String expectedUsername) {
        Claims claims = extractAllClaims(token);
        String username = claims.getSubject();
        return expectedUsername != null
                && expectedUsername.equals(username)
                && !isTokenExpired(claims);
    }

    private boolean isTokenExpired(Claims claims) {
        return claims.getExpiration().before(new Date());
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}