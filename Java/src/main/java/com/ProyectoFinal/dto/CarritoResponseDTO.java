package com.ProyectoFinal.dto;

import java.util.List;

import com.ProyectoFinal.model.Carrito;

public record CarritoResponseDTO(
    Long id,
    List<CarritoProductoResponseDTO> items,
    double total,
    int cantidadTotalItems
) {
    public static CarritoResponseDTO from(Carrito c) {
        List<CarritoProductoResponseDTO> itemsDto = c.getItems().stream()
            .map(CarritoProductoResponseDTO::from)
            .toList();

        int cantidadTotal = itemsDto.stream()
            .mapToInt(CarritoProductoResponseDTO::cantidad)
            .sum();

        return new CarritoResponseDTO(
            c.getId(),
            itemsDto,
            c.calcularTotal(),
            cantidadTotal
        );
    }
}
