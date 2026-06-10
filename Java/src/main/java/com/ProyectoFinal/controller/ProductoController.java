package com.ProyectoFinal.controller;

import org.springframework.web.bind.annotation.*;
import java.util.List;

import com.ProyectoFinal.model.Producto;
import com.ProyectoFinal.service.ProductoService;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping
    public List<Producto> listarProductos() {
        return productoService.listarTodos();
    }
}