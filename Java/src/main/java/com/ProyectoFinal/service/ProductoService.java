package com.ProyectoFinal.service;

import org.springframework.stereotype.Service;

import com.ProyectoFinal.dto.ProductoRequestDTO;
import com.ProyectoFinal.dto.ProductoResponseDTO;
import com.ProyectoFinal.exception.CategoriaNoEncontradaException;
import com.ProyectoFinal.exception.ProductoNoEncontradoException;
import com.ProyectoFinal.model.Categoria;
import com.ProyectoFinal.model.Producto;
import com.ProyectoFinal.repository.CategoriaRepository;
import com.ProyectoFinal.repository.ProductoRepository;
import java.util.List;

@Service
public class ProductoService {

    private final ProductoRepository repository;
    private final CategoriaRepository categoriaRepository;

    public ProductoService(ProductoRepository repository, CategoriaRepository categoriaRepository) {
        this.repository = repository;
        this.categoriaRepository = categoriaRepository;
    }

    public ProductoResponseDTO guardar(ProductoRequestDTO dto) {
        Producto producto = new Producto();
        producto.setNombre(dto.nombre());
        producto.setPrecio(dto.precio());
        producto.setStock(dto.stock());

        if (dto.categoriaId() != null) {
            Categoria categoria = categoriaRepository.findById(dto.categoriaId())
                .orElseThrow(() -> new CategoriaNoEncontradaException(
                    "Categoría no encontrada con ID: " + dto.categoriaId()));
            producto.setCategoria(categoria);
        }

        Producto guardado = repository.save(producto);
        return ProductoResponseDTO.from(guardado);
    }

    public List<ProductoResponseDTO> listarTodos() {
        return repository.findAll().stream()
            .map(ProductoResponseDTO::from)
            .toList();
    }

    public ProductoResponseDTO obtenerPorId(long id) {
        Producto producto = repository.findById(id)
                .orElseThrow(() -> new ProductoNoEncontradoException("Producto no encontrado con ID: " + id));
        return ProductoResponseDTO.from(producto);
    }

    public void eliminarPorId(long id) {
        if (!repository.existsById(id)) {
            throw new ProductoNoEncontradoException("Producto no encontrado con ID: " + id);
        }
        repository.deleteById(id);
    }

    public ProductoResponseDTO actualizar(long id, ProductoRequestDTO dto) {
        Producto existente = repository.findById(id)
                .orElseThrow(() -> new ProductoNoEncontradoException("Producto no encontrado con ID: " + id));

        existente.setNombre(dto.nombre());
        existente.setPrecio(dto.precio());
        existente.setStock(dto.stock());

        if (dto.categoriaId() != null) {
            Categoria categoria = categoriaRepository.findById(dto.categoriaId())
                .orElseThrow(() -> new CategoriaNoEncontradaException(
                    "Categoría no encontrada con ID: " + dto.categoriaId()));
            existente.setCategoria(categoria);
        } else {
            existente.setCategoria(null);
        }

        Producto actualizado = repository.save(existente);
        return ProductoResponseDTO.from(actualizado);
    }

    public List<ProductoResponseDTO> buscarPorNombre(String nombre) {
        return repository.findByNombreContaining(nombre).stream()
            .map(ProductoResponseDTO::from)
            .toList();
    }

    public List<ProductoResponseDTO> buscarPorCategoria(String categoria) {
        return repository.buscarPorCategoria(categoria).stream()
            .map(ProductoResponseDTO::from)
            .toList();
    }
}
