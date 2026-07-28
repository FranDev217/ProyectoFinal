package com.ProyectoFinal.dto;

import com.ProyectoFinal.model.CarritoProducto;

public record CarritoProductoResponseDTO(
    Long id,
    Long productoId,
    String productoNombre,
    double productoPrecio,
    int cantidad,
    double subtotal
) {
    public static CarritoProductoResponseDTO from(CarritoProducto cp) {
        double subtotal = cp.getProducto().getPrecio() * cp.getCantidad();
        return new CarritoProductoResponseDTO(
            cp.getId(),
            cp.getProducto().getId(),
            cp.getProducto().getNombre(),
            cp.getProducto().getPrecio(),
            cp.getCantidad(),
            subtotal
        );
    }
}
