package com.example.bodega.producto.mapper;
import com.example.bodega.producto.dto.ProductoResponse; import com.example.bodega.producto.entity.Producto;
public final class ProductoMapper { private ProductoMapper() {} public static ProductoResponse toResponse(Producto p){return new ProductoResponse(p.getId(),p.getNombre(),p.getDescripcion(),p.getCodigoBarras(),p.getCategoria().getId(),p.getCategoria().getNombre(),p.getPrecioCompra(),p.getPrecioVenta(),p.getStockActual(),p.getStockMinimo(),p.getUnidadMedida(),p.isActivo(),p.getCreatedAt(),p.getUpdatedAt());} }
