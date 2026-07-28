package com.ProyectoFinal.dto;

import com.ProyectoFinal.model.Producto;

public record ProductoResponseDTO(
    Long id,
    String nombre,
    double precio,
    int stock,
    String categoriaNombre
) {
    public static ProductoResponseDTO from(Producto p) {
        return new ProductoResponseDTO(
            p.getId(),
            p.getNombre(),
            p.getPrecio(),
            p.getStock(),
            p.getCategoria() != null ? p.getCategoria().getNombre() : null
        );
    }
}
