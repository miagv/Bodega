package com.example.bodega.venta.dto;
import java.math.BigDecimal;
public record DetalleVentaResponse(Long productoId,String producto,int cantidad,BigDecimal precioUnitario,BigDecimal subtotal) { }
