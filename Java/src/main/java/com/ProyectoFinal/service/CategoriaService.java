package com.ProyectoFinal.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ProyectoFinal.dto.CategoriaRequestDTO;
import com.ProyectoFinal.dto.CategoriaResponseDTO;
import com.ProyectoFinal.exception.CategoriaNombreInvalidoException;
import com.ProyectoFinal.exception.CategoriaNoEncontradaException;
import com.ProyectoFinal.model.Categoria;
import com.ProyectoFinal.repository.CategoriaRepository;

@Service
public class CategoriaService {

    private final CategoriaRepository repository;

    public CategoriaService(CategoriaRepository repository) {
        this.repository = repository;
    }

    public CategoriaResponseDTO guardar(CategoriaRequestDTO dto) {
        validarCategoria(dto);
        Categoria categoria = new Categoria(dto.nombre(), dto.descripcion());
        Categoria guardada = repository.save(categoria);
        return CategoriaResponseDTO.from(guardada);
    }

    public List<CategoriaResponseDTO> listarTodos() {
        return repository.findAll().stream()
            .map(CategoriaResponseDTO::from)
            .toList();
    }

    public CategoriaResponseDTO obtenerPorId(long id) {
        Categoria categoria = repository.findById(id)
                .orElseThrow(() -> new CategoriaNoEncontradaException("Categoría no encontrada con ID: " + id));
        return CategoriaResponseDTO.from(categoria);
    }

    public void eliminarPorId(long id) {
        if (!repository.existsById(id)) {
            throw new CategoriaNoEncontradaException("Categoría no encontrada con ID: " + id);
        }
        repository.deleteById(id);
    }

    public CategoriaResponseDTO actualizar(long id, CategoriaRequestDTO dto) {
        Categoria existente = repository.findById(id)
                .orElseThrow(() -> new CategoriaNoEncontradaException("Categoría no encontrada con ID: " + id));

        validarCategoria(dto);

        existente.setNombre(dto.nombre());
        existente.setDescripcion(dto.descripcion());

        Categoria actualizada = repository.save(existente);
        return CategoriaResponseDTO.from(actualizada);
    }

    private void validarCategoria(CategoriaRequestDTO dto) {
        if (dto.nombre() == null || dto.nombre().trim().isEmpty()) {
            throw new CategoriaNombreInvalidoException("El nombre de la categoría no puede estar vacío.");
        }
    }
}
