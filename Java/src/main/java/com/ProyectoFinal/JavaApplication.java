package com.ProyectoFinal;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.ProyectoFinal.dto.CategoriaRequestDTO;
import com.ProyectoFinal.dto.ProductoRequestDTO;
import com.ProyectoFinal.service.ProductoService;
import com.ProyectoFinal.service.CategoriaService;

@SpringBootApplication
public class JavaApplication {

	public static void main(String[] args) {
		SpringApplication.run(JavaApplication.class, args);
	}

	@Bean
	CommandLineRunner cargarDatos(ProductoService productoService, CategoriaService categoriaService) {
		return args -> {
			if (productoService.listarTodos().isEmpty()) {
				var almacen = categoriaService.guardar(new CategoriaRequestDTO("Almacen", "Productos de almacen"));
				var bebidas = categoriaService.guardar(new CategoriaRequestDTO("Bebidas", "Bebidas y liquidos"));
				productoService.guardar(new ProductoRequestDTO("Producto 1", 1500, 19, almacen.id()));
				productoService.guardar(new ProductoRequestDTO("Producto 2", 2000, 5, almacen.id()));
				productoService.guardar(new ProductoRequestDTO("Producto 3", 3200, 10, almacen.id()));
				productoService.guardar(new ProductoRequestDTO("Producto 4", 4200, 15, almacen.id()));
				productoService.guardar(new ProductoRequestDTO("Manaos", 2200, 15, bebidas.id()));
			}
		};
	}
}
