package com.ProyectoFinal.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "carrito")
public class Carrito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany(mappedBy = "carrito", cascade = { CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.REMOVE }, orphanRemoval = true)
    private List<CarritoProducto> items = new ArrayList<>();

    public Carrito() {
    }

    public Carrito(List<CarritoProducto> items) {
        this.items = items != null ? items : new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<CarritoProducto> getItems() {
        return items;
    }

    public void setItems(List<CarritoProducto> items) {
        this.items = items != null ? items : new ArrayList<>();
    }

    public void agregarProducto(Producto producto, int cantidad) {
        if (producto == null || cantidad <= 0) {
            return;
        }

        for (CarritoProducto item : items) {
            if (item.getProducto().getId() == producto.getId()) {
                item.setCantidad(item.getCantidad() + cantidad);
                return;
            }
        }

        CarritoProducto nuevoItem = new CarritoProducto(this, producto, cantidad);
        items.add(nuevoItem);
    }

    public void eliminarProducto(Producto producto) {
        items.removeIf(item -> item.getProducto().getId() == producto.getId());
    }

    public void vaciar() {
        items.clear();
    }

    public double calcularTotal() {
        return items.stream()
                .mapToDouble(item -> item.getProducto().getPrecio() * item.getCantidad())
                .sum();
    }

    @Override
    public String toString() {
        return "Carrito ID: " + id + " | Productos: " + items.size() + " | Total: $" + calcularTotal();
    }
}
