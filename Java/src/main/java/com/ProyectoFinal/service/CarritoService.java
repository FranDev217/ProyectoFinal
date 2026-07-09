package com.ProyectoFinal.service;

import java.util.List;

import org.springframework.stereotype.Service;

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

    public Carrito guardar() {
        return carritoRepository.save(new Carrito());
    }

    public List<Carrito> listarTodos() {
        return carritoRepository.findAllConProductos();
    }

    public Carrito obtenerPorId(long id) {
        return carritoRepository.findByIdConProductos(id)
                .orElseThrow(() -> new IllegalArgumentException("Carrito no encontrado con ID: " + id));
    }

    public Carrito agregarProducto(long carritoId, long productoId) {
        return agregarProducto(carritoId, productoId, 1);
    }

    public Carrito agregarProducto(long carritoId, long productoId, int cantidad) {
        Carrito carrito = obtenerPorId(carritoId);
        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new ProductoNoEncontradoException("Producto no encontrado con ID: " + productoId));

        carrito.agregarProducto(producto, cantidad);
        return carritoRepository.save(carrito);
    }

    public void eliminarCarrito(long carritoId) {
        if (!carritoRepository.existsById(carritoId)) {
            throw new IllegalArgumentException("Carrito no encontrado con ID: " + carritoId);
        }
        carritoRepository.deleteById(carritoId);
    }

    public Carrito eliminarProducto(long carritoId, long productoId) {
        Carrito carrito = obtenerPorId(carritoId);
        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new ProductoNoEncontradoException("Producto no encontrado con ID: " + productoId));

        carrito.eliminarProducto(producto);
        return carritoRepository.save(carrito);
    }

    public void vaciar(long carritoId) {
        Carrito carrito = obtenerPorId(carritoId);
        carrito.vaciar();
        carritoRepository.save(carrito);
    }

    public double calcularTotal(long carritoId) {
        return obtenerPorId(carritoId).calcularTotal();
    }
}
