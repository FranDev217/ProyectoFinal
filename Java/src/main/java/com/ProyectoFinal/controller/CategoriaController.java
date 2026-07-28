package com.ProyectoFinal.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ProyectoFinal.dto.CategoriaRequestDTO;
import com.ProyectoFinal.dto.CategoriaResponseDTO;
import com.ProyectoFinal.exception.CategoriaNoEncontradaException;
import com.ProyectoFinal.exception.CategoriaNombreInvalidoException;
import com.ProyectoFinal.service.CategoriaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/categorias")
@CrossOrigin
public class CategoriaController {

    private final CategoriaService service;

    public CategoriaController(CategoriaService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<CategoriaResponseDTO>> listarTodas() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> obtenerCategoria(@PathVariable long id) {
        try {
            return ResponseEntity.ok(service.obtenerPorId(id));
        } catch (CategoriaNoEncontradaException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<CategoriaResponseDTO> crearCategoria(@Valid @RequestBody CategoriaRequestDTO nuevaCategoria) {
        try {
            CategoriaResponseDTO creada = service.guardar(nuevaCategoria);
            return ResponseEntity.status(HttpStatus.CREATED).body(creada);
        } catch (CategoriaNombreInvalidoException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> actualizar(@PathVariable long id, @Valid @RequestBody CategoriaRequestDTO datos) {
        try {
            return ResponseEntity.ok(service.actualizar(id, datos));
        } catch (CategoriaNoEncontradaException e) {
            return ResponseEntity.notFound().build();
        } catch (CategoriaNombreInvalidoException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable long id) {
        try {
            service.eliminarPorId(id);
            return ResponseEntity.ok().build();
        } catch (CategoriaNoEncontradaException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
