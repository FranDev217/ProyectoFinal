package com.ProyectoFinal.service;

import java.util.ArrayList;
import org.springframework.stereotype.Service;
import java.util.List;

import com.ProyectoFinal.model.Producto;

@Service
public class ProductoService {

    private List<Producto> productos = new ArrayList<>();

    // AGREGA ESTE CONSTRUCTOR DE PRUEBA:
    public ProductoService() {
        // Usamos los tipos que definiste: id (Long), nombre (String), precio (double)
        // this.crearProducto(1L, "Coca Cola", 1500.0, 10);
        // this.crearProducto(2L, "Papas Fritas", 2200.0, 5);
    }

    public List<Producto> listarTodos() {
        return productos;
    }

    public void crearProducto(Long id, String nombre, double precio, int stock, String categoria) {
        Producto nuevoProducto = new Producto(id, nombre, precio, stock, categoria);
        productos.add(nuevoProducto);
    }

}
