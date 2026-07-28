package com.ProyectoFinal.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ProyectoFinal.dto.CarritoResponseDTO;
import com.ProyectoFinal.exception.ProductoNoEncontradoException;
import com.ProyectoFinal.service.CarritoService;

@RestController
@RequestMapping("/carritos")
@CrossOrigin
public class CarritoController {

    private final CarritoService service;

    public CarritoController(CarritoService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<CarritoResponseDTO>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarritoResponseDTO> obtenerCarrito(@PathVariable long id) {
        try {
            return ResponseEntity.ok(service.obtenerPorId(id));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<CarritoResponseDTO> crearCarrito() {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar());
    }

    @PostMapping("/{carritoId}/productos/{productoId}")
    public ResponseEntity<CarritoResponseDTO> agregarProducto(
            @PathVariable long carritoId,
            @PathVariable long productoId,
            @RequestParam(defaultValue = "1") int cantidad) {
        try {
            return ResponseEntity.ok(service.agregarProducto(carritoId, productoId, cantidad));
        } catch (ProductoNoEncontradoException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{carritoId}/productos/{productoId}")
    public ResponseEntity<CarritoResponseDTO> eliminarProducto(@PathVariable long carritoId, @PathVariable long productoId) {
        try {
            return ResponseEntity.ok(service.eliminarProducto(carritoId, productoId));
        } catch (ProductoNoEncontradoException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCarrito(@PathVariable long id) {
        try {
            service.eliminarCarrito(id);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}/vaciar")
    public ResponseEntity<Void> vaciarCarrito(@PathVariable long id) {
        try {
            service.vaciar(id);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/total")
    public ResponseEntity<Double> calcularTotal(@PathVariable long id) {
        try {
            return ResponseEntity.ok(service.calcularTotal(id));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
