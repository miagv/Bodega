package com.example.bodega.categoria.mapper;
import com.example.bodega.categoria.dto.CategoriaResponse;
import com.example.bodega.categoria.entity.Categoria;
public final class CategoriaMapper { private CategoriaMapper() {} public static CategoriaResponse toResponse(Categoria c) { return new CategoriaResponse(c.getId(),c.getNombre(),c.getDescripcion(),c.isActiva(),c.getCreatedAt(),c.getUpdatedAt()); } }
