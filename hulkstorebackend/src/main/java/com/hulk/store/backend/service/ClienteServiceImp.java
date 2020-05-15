package com.hulk.store.backend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import com.hulk.store.backend.dao.IClienteDao;
import com.hulk.store.backend.dao.IFacturaDao;
import com.hulk.store.backend.dao.IProductoDao;
import com.hulk.store.backend.entity.Cliente;
import com.hulk.store.backend.entity.Factura;
import com.hulk.store.backend.entity.Producto;

@Service
public class ClienteServiceImp implements IClienteService {

	//implementamos la interfaz de IClienteService
	
	//utilizamos el autowired para inyectar la clase dao
	@Autowired
	private IClienteDao clienteDao; 
	
	@Autowired
	private IFacturaDao facturaDao;
	
	@Autowired
	private IProductoDao productoDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Cliente> finAll() {
		return (List<Cliente>) clienteDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Cliente findById(Long id) {
		return clienteDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Cliente save(Cliente cliente) {
		return clienteDao.save(cliente);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		clienteDao.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Factura findFacturaById(Long id) {
		return facturaDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Factura saveFactura(Factura factura) {
		return facturaDao.save(factura);
	}

	@Override
	@Transactional
	public void deleteFacturaById(Long id) {
		facturaDao.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Producto> findProductoByNombre(String term) {
		return productoDao.findByNombre(term);
	}

	@Override
	public List<String> validacion(BindingResult result) {
		return result.getFieldErrors().stream()
				.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
				.collect(Collectors.toList());
	}

	@Override
	public Cliente Actualizar(Cliente clienteActual, Cliente Actualizado) {
		clienteActual.setNit(Actualizado.getNit());
		clienteActual.setNombres(Actualizado.getNombres());
		clienteActual.setApellidos(Actualizado.getApellidos());
		clienteActual.setTelefono(Actualizado.getTelefono());
		clienteActual.setGenero(Actualizado.getGenero());
		
		return clienteActual;
	}

	@Override
	public int restarStock(int stock, int cantidad) {
		return stock-cantidad;
	}


}
