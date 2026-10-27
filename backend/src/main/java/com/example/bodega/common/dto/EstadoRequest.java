package com.example.bodega.common.dto;

import jakarta.validation.constraints.NotNull;

public record EstadoRequest(@NotNull Boolean activo) { }
