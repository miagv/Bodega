package com.example.bodega.reporte.dto;
import java.math.BigDecimal; import java.time.LocalDate; import java.util.Map;
public record ReporteVentasResponse(LocalDate desde,LocalDate hasta,long cantidadVentas,BigDecimal totalVentas,Map<String,BigDecimal> porMetodoPago) { }
