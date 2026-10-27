package com.example.bodega.auth.dto;
public record AuthResponse(String token, String tokenType, long expiresInSeconds, Long usuarioId, String username, String rol) { }
