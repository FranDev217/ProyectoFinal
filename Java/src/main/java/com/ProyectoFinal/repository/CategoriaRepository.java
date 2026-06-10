package com.ProyectoFinal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ProyectoFinal.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

}