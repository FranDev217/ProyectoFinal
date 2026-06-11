package com.ProyectoFinal.service;

import org.springframework.stereotype.Service;

import com.ProyectoFinal.exception.ProductoNoEncontradoException;
import com.ProyectoFinal.model.Producto;
import com.ProyectoFinal.repository.ProductoRepository;
import java.util.List;

@Service
public class ProductoService {

    // Inyección por constructor : Spring pasa el repositorio.
    private final ProductoRepository repository;

    public ProductoService(ProductoRepository repository) {
        this.repository = repository;
    }

    public Producto guardar(Producto p) {
        if (p.getNombre() == null || p.getNombre().isBlank()) {
            throw new IllegalArgumentException("El nombre del producto no puede ser nulo o vacío");
        }
        if (p.getPrecio() < 0) {
            throw new IllegalArgumentException("El precio del producto no puede ser negativo");
        }
        if (p.getStock() < 0) {
            throw new IllegalArgumentException("El stock del producto no puede ser negativo");
        }
        return repository.save(p);
    }

    public List<Producto> listarTodos() {
        return repository.findAll();
    }

    public Producto obtenerPorId(long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ProductoNoEncontradoException("Producto no encontrado con ID: " + id));
    }

    public void eliminarPorId(long id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Producto no encontrado con ID: " + id);
        }
        repository.deleteById(id);
    }

    public Producto actualizar(long id, Producto p) {
        Producto existente = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado con ID: " + id));

        if (p.getNombre() != null && !p.getNombre().isBlank()) {
            existente.setNombre(p.getNombre());
        }
        if (p.getPrecio() <= 0) {
            existente.setPrecio(p.getPrecio());
        }
        if (p.getStock() < 0) {
            existente.setStock(p.getStock());
        }
        if (p.getCategoria() != null && !p.getCategoria().isBlank()) {
            existente.setCategoria(p.getCategoria());
        }

        return repository.save(existente);
    }

    public List<Producto> buscarPorNombre(String nombre) {
        return repository.findByNombreContaining(nombre);
    }

    public List<Producto> buscarPorCategoria(String categoria) {
        return repository.buscarPorCategoria(categoria);
    }

}
