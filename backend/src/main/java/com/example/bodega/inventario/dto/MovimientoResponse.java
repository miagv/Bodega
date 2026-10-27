package com.example.bodega.inventario.dto;
import com.example.bodega.inventario.enums.TipoMovimiento; import java.time.Instant;
public record MovimientoResponse(Long id,Long productoId,TipoMovimiento tipo,int cantidad,int stockAnterior,int stockPosterior,String motivo,String usuario,Instant fecha) { }
