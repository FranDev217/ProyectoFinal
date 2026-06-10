package com.ProyectoFinal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ProyectoFinal.model.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

}