package com.hulk.store.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


}
