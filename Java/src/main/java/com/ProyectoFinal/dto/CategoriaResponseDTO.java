package com.ProyectoFinal.dto;

import com.ProyectoFinal.model.Categoria;

public record CategoriaResponseDTO(
    Long id,
    String nombre,
    String descripcion
) {
    public static CategoriaResponseDTO from(Categoria c) {
        return new CategoriaResponseDTO(
            c.getId(),
            c.getNombre(),
            c.getDescripcion()
        );
    }
}
