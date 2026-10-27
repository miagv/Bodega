package com.example.bodega.categoria.dto;
import java.time.Instant;
public record CategoriaResponse(Long id, String nombre, String descripcion, boolean activa, Instant createdAt, Instant updatedAt) { }
