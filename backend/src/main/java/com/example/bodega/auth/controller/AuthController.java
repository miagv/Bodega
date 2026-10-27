package com.example.bodega.auth.controller;
import com.example.bodega.auth.dto.*; import com.example.bodega.auth.service.AuthService; import jakarta.validation.Valid; import lombok.RequiredArgsConstructor; import org.springframework.http.*; import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/auth") @RequiredArgsConstructor
public class AuthController { private final AuthService service; @PostMapping("/login") public AuthResponse login(@Valid @RequestBody LoginRequest r){return service.login(r);}@PostMapping("/logout") @ResponseStatus(HttpStatus.NO_CONTENT) public void logout(){} }
