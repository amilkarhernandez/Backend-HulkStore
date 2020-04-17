package com.hulk.store.backend.dao;

import org.springframework.data.repository.CrudRepository;

import com.hulk.store.backend.entity.Factura;

public interface IFacturaDao extends CrudRepository<Factura, Long>{
	//Creamos el repositorio extendiendo una clase que se llama CrudRepository, que ya nos provee
		//metodos basicos para hacer un crud entre otros.
		//crecibe dos parametros, la clase, y el tipo de datos de la llave primaria
}
