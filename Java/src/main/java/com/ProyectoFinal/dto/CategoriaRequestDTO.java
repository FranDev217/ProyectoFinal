package com.ProyectoFinal.dto;

import jakarta.validation.constraints.NotBlank;

public record CategoriaRequestDTO(
    @NotBlank(message = "El nombre de la categoría no puede estar vacío.")
    String nombre,

    String descripcion
) {}
