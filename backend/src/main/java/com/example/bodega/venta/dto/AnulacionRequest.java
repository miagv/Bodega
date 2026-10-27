package com.example.bodega.venta.dto;
import jakarta.validation.constraints.*;
public record AnulacionRequest(@NotBlank @Size(max=300) String motivo) { }
