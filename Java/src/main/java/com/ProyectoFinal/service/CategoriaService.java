package com.ProyectoFinal.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ProyectoFinal.model.Categoria;
import com.ProyectoFinal.repository.CategoriaRepository;

@Service
public class CategoriaService {
    private final CategoriaRepository repository;

    public CategoriaService(CategoriaRepository repository) {
        this.repository = repository;

    }

    public Categoria guardar(Categoria c) {
        if (c.getNombre() == null || c.getNombre().isBlank()) {
            throw new IllegalArgumentException("El nombre de la categoría no puede ser nulo o vacío");
        }
        return repository.save(c);
    }

    public List<Categoria> listarTodos() {
        return repository.findAll();
    }

    public Categoria obtenerPorId(long id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Categoría no encontrada con ID: " + id));
    }

    public void eliminarPorId(long id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Categoría no encontrada con ID: " + id);
        }
        repository.deleteById(id);
    }

    public Categoria actualizar(long id, Categoria c) {
        Categoria existente = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Categoría no encontrada con ID: " + id));

        if (c.getNombre() != null && !c.getNombre().isBlank()) {
            existente.setNombre(c.getNombre());
            existente.setDescripcion(c.getDescripcion());
        }
        return repository.save(existente);
    }

}
