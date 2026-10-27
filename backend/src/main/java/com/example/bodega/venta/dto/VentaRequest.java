package com.example.bodega.venta.dto;
import com.example.bodega.venta.enums.MetodoPago; import jakarta.validation.Valid; import jakarta.validation.constraints.*; import java.util.List;
public record VentaRequest(@NotNull MetodoPago metodoPago,@NotEmpty List<@Valid DetalleVentaRequest> detalles) { }
