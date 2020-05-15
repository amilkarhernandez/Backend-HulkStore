package com.hulk.store.backend.service;

import java.util.List;

import org.springframework.validation.BindingResult;

import com.hulk.store.backend.entity.Categoria;

public interface ICategoriaService {

	//declaramos una interfaz con los metodos, para luego implementarlos con la clase dao
	
	public List<Categoria> findAll();
	
	public Categoria findById(Long id);
	
	public Categoria save(Categoria categoria);
	
	public void delete(Long id);
	
	public List<String> validacion(BindingResult result);
	
	public Categoria Actualizar(Categoria categoriaActual, Categoria Actualizado);
	
}
