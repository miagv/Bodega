package com.example.bodega.venta.dto;
import jakarta.validation.constraints.*;
public record DetalleVentaRequest(@NotNull Long productoId,@NotNull @Min(1) Integer cantidad) { }
