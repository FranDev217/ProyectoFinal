package com.ProyectoFinal;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.ProyectoFinal.model.Producto;
import com.ProyectoFinal.service.ProductoService;
import com.ProyectoFinal.model.Categoria;
import com.ProyectoFinal.service.CategoriaService;

@SpringBootApplication
public class JavaApplication {

	public static void main(String[] args) {
		SpringApplication.run(JavaApplication.class, args);
	}

	// Carga de datos iniciales para pruebas
	@Bean
	CommandLineRunner cargarDatos(ProductoService productoService, CategoriaService categoriaService) {
		return args -> {
			if (productoService.listarTodos().isEmpty()) {

				Categoria almacen = categoriaService.guardar(new Categoria("Almacén", "Productos de almacén"));
				Categoria bebidas = categoriaService.guardar(new Categoria("Bebidas", "Bebidas y líquidos"));
				productoService.guardar(new Producto("Producto 1", 1500, 19, almacen));
				productoService.guardar(new Producto("Producto 2", 2000, 5, almacen));
				productoService.guardar(new Producto("Producto 3", 3200, 10, almacen));
				productoService.guardar(new Producto("Producto 4", 4200, 15, almacen));
				productoService.guardar(new Producto("Manaos", 2200, 15, bebidas));
			}
		};

	}

}
