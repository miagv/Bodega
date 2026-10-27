package com.example.bodega.venta.dto;
import com.example.bodega.venta.enums.*; import java.math.BigDecimal; import java.time.Instant; import java.util.List;
public record VentaResponse(Long id,String numero,String usuario,Instant fecha,MetodoPago metodoPago,EstadoVenta estado,BigDecimal total,List<DetalleVentaResponse> detalles) { }
