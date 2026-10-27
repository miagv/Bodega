package com.example.bodega.usuario.dto;
import com.example.bodega.usuario.enums.Role; import jakarta.validation.constraints.*;
public record UsuarioRequest(@NotBlank @Size(max=50) String username,@NotBlank @Size(max=120) String nombre,@NotBlank @Size(min=8,max=100) String password,@NotNull Role rol) { }
