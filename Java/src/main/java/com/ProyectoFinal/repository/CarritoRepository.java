package com.ProyectoFinal.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ProyectoFinal.model.Carrito;

public interface CarritoRepository extends JpaRepository<Carrito, Long> {

    @Query("SELECT c FROM Carrito c LEFT JOIN FETCH c.items i LEFT JOIN FETCH i.producto WHERE c.id = :id")
    Optional<Carrito> findByIdConProductos(long id);

    @Query("SELECT DISTINCT c FROM Carrito c LEFT JOIN FETCH c.items i LEFT JOIN FETCH i.producto")
    List<Carrito> findAllConProductos();
}

/*
 * ¿Por qué el DISTINCT?
 * Cuando hacés un JOIN FETCH sobre una colección (productos), SQL devuelve una
 * fila por cada combinación de carrito-producto. Entonces si el carrito 1 tiene
 * 3 productos, sin DISTINCT te aparecería el carrito 1 repetido 3 veces en la
 * lista de resultados (aunque Hibernate arma bien los objetos, a nivel de la
 * consulta SQL vienen filas duplicadas). El DISTINCT en la consulta JPQL le
 * dice
 * a Hibernate que elimine esos carritos duplicados del resultado final en Java.
 */