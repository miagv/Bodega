package com.example.bodega.venta.mapper;
import com.example.bodega.venta.dto.*; import com.example.bodega.venta.entity.*;
public final class VentaMapper { private VentaMapper(){} public static VentaResponse toResponse(Venta v){return new VentaResponse(v.getId(),v.getNumero(),v.getUsuario().getUsername(),v.getFecha(),v.getMetodoPago(),v.getEstado(),v.getTotal(),v.getDetalles().stream().map(d->new DetalleVentaResponse(d.getProducto().getId(),d.getProducto().getNombre(),d.getCantidad(),d.getPrecioUnitario(),d.getSubtotal())).toList());} }
