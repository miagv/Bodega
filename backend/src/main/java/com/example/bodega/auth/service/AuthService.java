package com.example.bodega.auth.service;
import com.example.bodega.auth.dto.*; import com.example.bodega.security.*;
import lombok.RequiredArgsConstructor; import org.springframework.beans.factory.annotation.Value; import org.springframework.security.authentication.*; import org.springframework.security.core.Authentication; import org.springframework.stereotype.Service;
@Service @RequiredArgsConstructor
public class AuthService { private final AuthenticationManager authenticationManager; private final JwtService jwt; @Value("${app.jwt.expiration-minutes}") private long expirationMinutes;
    public AuthResponse login(LoginRequest r){Authentication a=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(r.username(),r.password()));SecurityUser u=(SecurityUser)a.getPrincipal();return new AuthResponse(jwt.generate(u),"Bearer",expirationMinutes*60,u.id(),u.username(),u.authorities().iterator().next().getAuthority().substring(5));}
}
