package com.hulk.store.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hulk.store.backend.dao.IProductoDao;
import com.hulk.store.backend.entity.Categoria;
import com.hulk.store.backend.entity.Producto;

@Service
public class ProductoServiceImp implements IProductoService {

	//implementamos la interfaz de IProductoService
	
	//utilizamos el autowired para inyectar la clase dao
	@Autowired
	private IProductoDao productoDao; 
	
	@Override
	public List<Producto> finAll() {
		return (List<Producto>) productoDao.findAll();
	}

	@Override
	public Producto findById(Long id) {
		return productoDao.findById(id).orElse(null);
	}

	@Override
	public Producto save(Producto producto) {
		return productoDao.save(producto);
	}

	@Override
	public void delete(Long id) {
		productoDao.deleteById(id);
	}

	@Override
	public List<Categoria> findAllCategorias() {
		
		return productoDao.findAllCategorias();
	}

}
