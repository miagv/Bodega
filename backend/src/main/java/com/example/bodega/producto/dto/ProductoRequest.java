package com.example.bodega.producto.dto;
import com.example.bodega.producto.enums.UnidadMedida;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
public record ProductoRequest(@NotBlank @Size(max=150) String nombre, @Size(max=500) String descripcion, @Size(max=100) String codigoBarras, @NotNull Long categoriaId, @NotNull @DecimalMin("0.00") BigDecimal precioCompra, @NotNull @DecimalMin("0.00") BigDecimal precioVenta, @Min(0) Integer stockInicial, @NotNull @Min(0) Integer stockMinimo, @NotNull UnidadMedida unidadMedida) { }
