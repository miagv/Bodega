package com.example.bodega.usuario.mapper;
import com.example.bodega.usuario.dto.UsuarioResponse; import com.example.bodega.usuario.entity.Usuario;
public final class UsuarioMapper { private UsuarioMapper(){} public static UsuarioResponse toResponse(Usuario u){return new UsuarioResponse(u.getId(),u.getUsername(),u.getNombre(),u.getRol(),u.isActivo(),u.getCreatedAt(),u.getUpdatedAt());} }
