package br.com.diegosantos.minierp.identity.infrastructure.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Service
public class JwtService {

    private static final int HS256_MIN_KEY_BYTES = 32;

    @Value("${security.jwt.secret}")
    private String jwtSecret;

    @Value("${security.jwt.expiration}")
    private long jwtExpirationInSeconds;

    private SecretKey signingKey;

    @PostConstruct
    private void initializeSigningKey() {
        this.signingKey = buildSigningKey(jwtSecret);
    }

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

    private SecretKey buildSigningKey(String secret) {
        if (secret == null || secret.trim().isEmpty()) {
            throw new IllegalStateException(
                    "Invalid JWT configuration: 'security.jwt.secret' must be configured and must not be blank. " +
                    "Provide a Base64-encoded secret or a plain text secret with at least 32 bytes for HS256."
            );
        }

        String normalizedSecret = secret.trim();
        byte[] keyBytes;

        try {
            keyBytes = Decoders.BASE64.decode(normalizedSecret);
        } catch (IllegalArgumentException ex) {
            keyBytes = normalizedSecret.getBytes(StandardCharsets.UTF_8);
        }

        if (keyBytes.length < HS256_MIN_KEY_BYTES) {
            throw new IllegalStateException(
                    "Invalid JWT configuration: 'security.jwt.secret' must decode to at least 32 bytes for HS256. " +
                    "Configure JWT_SECRET as a Base64-encoded 32-byte+ key or use a plain text secret with at least 32 characters."
            );
        }

        return Keys.hmacShaKeyFor(keyBytes);
    }

    private SecretKey getSigningKey() {
        return signingKey;
    }
}