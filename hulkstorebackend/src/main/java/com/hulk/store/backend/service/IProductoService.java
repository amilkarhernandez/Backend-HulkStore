package com.hulk.store.backend.service;

import java.util.List;

import org.springframework.validation.BindingResult;

import com.hulk.store.backend.entity.Categoria;
import com.hulk.store.backend.entity.Producto;

public interface IProductoService {
	
	//declaramos una interfaz con los metodos, para luego implementarlos con la clase dao
	
	public List<Producto> finAll();

	public Producto findById(Long id);

	public Producto save(Producto producto);

	public void delete(Long id);
	
	public List<Categoria> findAllCategorias();
	
	public List<String> validacion(BindingResult result);
	
	public Producto Actualizar(Producto productoActual, Producto Actualizado);

}
