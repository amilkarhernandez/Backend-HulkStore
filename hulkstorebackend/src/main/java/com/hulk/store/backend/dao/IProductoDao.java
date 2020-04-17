package com.hulk.store.backend.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.hulk.store.backend.entity.Categoria;
import com.hulk.store.backend.entity.Producto;

public interface IProductoDao extends CrudRepository<Producto, Long>{
	//Creamos el repositorio extendiendo una clase que se llama CrudRepository, que ya nos provee
		//metodos basicos para hacer un crud entre otros.
		//crecibe dos parametros, la clase, y el tipo de datos de la llave primaria
	
	//query para las Categorias
	//creamos una consulta para listar categorias, lo hacemos por una consulta JPA.
		@Query("from Categoria")
		public List<Categoria> findAllCategorias();
		
	//creamos una consulta fuera de los metodos que trae el Crud Reporsitory
	//buscamos el prducto por descripcion, ademas hacemos la condicion para que busque por cualquier
	//incidencia, lo hacemos con like, y le decimos que nos muestre el producto cuando el stock sea mayor a cero
		@Query("select p from Producto p where p.descripcion like %?1% and p.stock > 0")
		public List<Producto> findByNombre(String term);
}
