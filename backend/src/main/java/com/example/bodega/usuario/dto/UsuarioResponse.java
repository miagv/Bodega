package com.example.bodega.usuario.dto;
import com.example.bodega.usuario.enums.Role; import java.time.Instant;
public record UsuarioResponse(Long id,String username,String nombre,Role rol,boolean activo,Instant createdAt,Instant updatedAt) { }
