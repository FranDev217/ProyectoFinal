package com.ProyectoFinal.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record ProductoRequestDTO(
    @NotBlank(message = "El nombre del producto no puede estar vacío.")
    String nombre,

    @Positive(message = "El precio debe ser mayor a cero.")
    double precio,

    @PositiveOrZero(message = "El stock no puede ser negativo.")
    int stock,

    Long categoriaId
) {}
