package com.example.bodega.inventario.dto;
import com.example.bodega.inventario.enums.TipoMovimiento;
import jakarta.validation.constraints.*;
public record MovimientoRequest(@NotNull Long productoId, @NotNull TipoMovimiento tipo, @NotNull @Min(1) Integer cantidad, @NotBlank @Size(max=300) String motivo, Boolean incrementar) { }
