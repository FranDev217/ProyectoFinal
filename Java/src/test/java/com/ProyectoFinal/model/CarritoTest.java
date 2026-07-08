package com.ProyectoFinal.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class CarritoTest {

    @Test
    void agregarProductoConCantidadActualizaElTotal() {
        Carrito carrito = new Carrito();
        Producto producto = new Producto("Teclado", 100.0, 10, null);

        carrito.agregarProducto(producto, 2);

        assertEquals(1, carrito.getItems().size());
        assertEquals(2, carrito.getItems().get(0).getCantidad());
        assertEquals(200.0, carrito.calcularTotal());
    }
}
