package com.example.bodega.producto.dto;
import com.example.bodega.producto.enums.UnidadMedida;
import java.math.BigDecimal; import java.time.Instant;
public record ProductoResponse(Long id,String nombre,String descripcion,String codigoBarras,Long categoriaId,String categoria,BigDecimal precioCompra,BigDecimal precioVenta,int stockActual,int stockMinimo,UnidadMedida unidadMedida,boolean activo,Instant createdAt,Instant updatedAt) { }
