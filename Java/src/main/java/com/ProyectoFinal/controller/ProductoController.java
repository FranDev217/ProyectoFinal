package com.ProyectoFinal.controller;

import java.util.List;

import com.ProyectoFinal.model.Producto;
import com.ProyectoFinal.service.ProductoService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ProyectoFinal.exception.ProductoNoEncontradoException;

import jakarta.validation.Valid;

// @RestController: maneja requests HTTP y serializa las respuestas a JSON.
// @RequestMapping: define la URL base de todos los endpoints de esta clase.
@RestController
@RequestMapping("/productos")
@CrossOrigin
public class ProductoController {

    // Inyección por constructor: Spring crea el ProductoService y lo pasa.
    private final ProductoService service;

    public ProductoController(ProductoService service) {
        this.service = service;
    }

    // GET /productos — 200 OK con la lista (puede estar vacía).
    @GetMapping
    public ResponseEntity<List<Producto>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    // GET /productos/{id} — 200 OK si existe, 404 si no.
    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerProducto(@PathVariable int id) {
        try {
            return ResponseEntity.ok(service.obtenerPorId(id));
        } catch (ProductoNoEncontradoException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // POST /productos — 201 Created con el producto creado.
    @PostMapping
    public ResponseEntity<Producto> crearProducto(@Valid @RequestBody Producto nuevoProducto) {
        Producto creado = service.guardar(nuevoProducto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    // PUT /productos/{id} — 200 OK si existe, 404 si no.
    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizar(@PathVariable long id, @Valid @RequestBody Producto datos) {
        try {
            return ResponseEntity.ok(service.actualizar(id, datos));
        } catch (ProductoNoEncontradoException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE /productos/{id} — 200 OK si existe, 404 si no.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable long id) {
        try {
            service.eliminarPorId(id);
            return ResponseEntity.ok().build();
        } catch (ProductoNoEncontradoException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // GET /productos/nombre/{nombre}
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<List<Producto>> buscarPorNombre(@PathVariable String nombre) {
        return ResponseEntity.ok(service.buscarPorNombre(nombre));
    }

    // GET /productos/categoria/{categoria}
    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<Producto>> buscarPorCategoria(@PathVariable String categoria) {
        return ResponseEntity.ok(service.buscarPorCategoria(categoria));
    }

}