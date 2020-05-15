package com.hulk.store.backend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.hulk.store.backend.dao.ICategoriaDao;
import com.hulk.store.backend.entity.Categoria;

@Service
public class CategoriaServiceImp implements ICategoriaService {

	//implementamos la interfaz de ICategoriaService
	
	//utilizamos el autowired para inyectar la clase dao
	@Autowired
	private ICategoriaDao categoriaDao;
	
	@Override
	public List<Categoria> findAll() {
		return (List<Categoria>) categoriaDao.findAll();
	}

	@Override
	public Categoria findById(Long id) {
		return categoriaDao.findById(id).orElse(null);
	}

	@Override
	public Categoria save(Categoria categoria) {
		return categoriaDao.save(categoria);
	}

	@Override
	public void delete(Long id) {
		categoriaDao.deleteById(id);
		
	}

	@Override
	public List<String> validacion(BindingResult result) {
		
		return result.getFieldErrors().stream()
				.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
				.collect(Collectors.toList());
	}

	@Override
	public Categoria Actualizar(Categoria categoriaActual, Categoria Actualizado) {
		
		categoriaActual.setDescripcion(Actualizado.getDescripcion());
		
		return categoriaActual;
	}


}
