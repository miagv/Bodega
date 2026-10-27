package com.example.bodega.security;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;

@Service
public class JwtService {
    private final SecretKey key; private final long expirationMillis;
    public JwtService(@Value("${app.jwt.secret}") String secret, @Value("${app.jwt.expiration-minutes}") long expirationMinutes) { this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret)); this.expirationMillis = expirationMinutes * 60_000; }
    public String generate(SecurityUser user) { Instant now = Instant.now(); return Jwts.builder().subject(user.username()).claim("uid", user.id()).claim("role", user.authorities().iterator().next().getAuthority()).issuedAt(Date.from(now)).expiration(Date.from(now.plusMillis(expirationMillis))).signWith(key).compact(); }
    public String username(String token) { return claims(token).getSubject(); }
    public boolean valid(String token, UserDetails user) { try { return user.getUsername().equals(username(token)) && claims(token).getExpiration().after(new Date()); } catch (JwtException | IllegalArgumentException e) { return false; } }
    private Claims claims(String token) { return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload(); }
}
