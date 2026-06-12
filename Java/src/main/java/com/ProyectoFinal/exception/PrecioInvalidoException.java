package com.ProyectoFinal.exception;

// Se lanza cuando se intenta guardar o actualizar un producto con precio <= 0.
public class PrecioInvalidoException extends RuntimeException {

    public PrecioInvalidoException(String mensaje) {
        super(mensaje);
    }
}