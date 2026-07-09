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
            throw new ProductoNoEncontradoException("Producto no encontrado con ID: " + id);
        }
        repository.deleteById(id);
    }

    public Producto actualizar(long id, Producto p) {
        Producto existente = repository.findById(id)
                .orElseThrow(() -> new ProductoNoEncontradoException("Producto no encontrado con ID: " + id));

        existente.setNombre(p.getNombre());

        existente.setPrecio(p.getPrecio());

        existente.setStock(p.getStock());

        existente.setCategoria(p.getCategoria());

        return repository.save(existente);
    }

    public List<Producto> buscarPorNombre(String nombre) {
        return repository.findByNombreContaining(nombre);
    }

    public List<Producto> buscarPorCategoria(String categoria) {
        return repository.buscarPorCategoria(categoria);
    }

}
