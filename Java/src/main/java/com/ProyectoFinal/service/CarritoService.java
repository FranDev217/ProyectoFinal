package com.ProyectoFinal.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ProyectoFinal.dto.CarritoResponseDTO;
import com.ProyectoFinal.exception.ProductoNoEncontradoException;
import com.ProyectoFinal.model.Carrito;
import com.ProyectoFinal.model.Producto;
import com.ProyectoFinal.repository.CarritoRepository;
import com.ProyectoFinal.repository.ProductoRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class CarritoService {

    private final CarritoRepository carritoRepository;
    private final ProductoRepository productoRepository;

    public CarritoService(CarritoRepository carritoRepository, ProductoRepository productoRepository) {
        this.carritoRepository = carritoRepository;
        this.productoRepository = productoRepository;
    }

    public CarritoResponseDTO guardar() {
        Carrito carrito = carritoRepository.save(new Carrito());
        return CarritoResponseDTO.from(carrito);
    }

    public List<CarritoResponseDTO> listarTodos() {
        return carritoRepository.findAllConProductos().stream()
            .map(CarritoResponseDTO::from)
            .toList();
    }

    public CarritoResponseDTO obtenerPorId(long id) {
        Carrito carrito = carritoRepository.findByIdConProductos(id)
                .orElseThrow(() -> new IllegalArgumentException("Carrito no encontrado con ID: " + id));
        return CarritoResponseDTO.from(carrito);
    }

    public CarritoResponseDTO agregarProducto(long carritoId, long productoId) {
        return agregarProducto(carritoId, productoId, 1);
    }

    public CarritoResponseDTO agregarProducto(long carritoId, long productoId, int cantidad) {
        Carrito carrito = carritoRepository.findByIdConProductos(carritoId)
            .orElseThrow(() -> new IllegalArgumentException("Carrito no encontrado con ID: " + carritoId));

        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new ProductoNoEncontradoException("Producto no encontrado con ID: " + productoId));

        carrito.agregarProducto(producto, cantidad);
        Carrito guardado = carritoRepository.save(carrito);
        return CarritoResponseDTO.from(guardado);
    }

    public void eliminarCarrito(long carritoId) {
        if (!carritoRepository.existsById(carritoId)) {
            throw new IllegalArgumentException("Carrito no encontrado con ID: " + carritoId);
        }
        carritoRepository.deleteById(carritoId);
    }

    public CarritoResponseDTO eliminarProducto(long carritoId, long productoId) {
        Carrito carrito = carritoRepository.findByIdConProductos(carritoId)
            .orElseThrow(() -> new IllegalArgumentException("Carrito no encontrado con ID: " + carritoId));

        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new ProductoNoEncontradoException("Producto no encontrado con ID: " + productoId));

        carrito.eliminarProducto(producto);
        Carrito guardado = carritoRepository.save(carrito);
        return CarritoResponseDTO.from(guardado);
    }

    public void vaciar(long carritoId) {
        Carrito carrito = carritoRepository.findByIdConProductos(carritoId)
            .orElseThrow(() -> new IllegalArgumentException("Carrito no encontrado con ID: " + carritoId));
        carrito.vaciar();
        carritoRepository.save(carrito);
    }

    public double calcularTotal(long carritoId) {
        Carrito carrito = carritoRepository.findByIdConProductos(carritoId)
            .orElseThrow(() -> new IllegalArgumentException("Carrito no encontrado con ID: " + carritoId));
        return carrito.calcularTotal();
    }
}
