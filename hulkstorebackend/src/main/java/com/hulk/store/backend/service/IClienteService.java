package com.hulk.store.backend.service;

import java.util.List;

import org.springframework.validation.BindingResult;

import com.hulk.store.backend.entity.Cliente;
import com.hulk.store.backend.entity.Factura;
import com.hulk.store.backend.entity.Producto;


public interface IClienteService {

	//declaramos una interfaz con los metodos, para luego implementarlos con la clase dao
	
	public List<Cliente> finAll();

	public Cliente findById(Long id);

	public Cliente save(Cliente cliente);

	public void delete(Long id);
	
	public List<String> validacion(BindingResult result);
	
	public Cliente Actualizar(Cliente clientectual, Cliente Actualizado);
	
	//Metodos para La Factura
	//Buscar por Id
	public Factura findFacturaById(Long id);
	
	//Guardar una Factura
	public Factura saveFactura(Factura factura);
	
	//Eliminar una Factura
	public void deleteFacturaById(Long id);
	
	//Listado de Productos para el Autocomplete
	public List<Producto> findProductoByNombre(String term);
	
	public int restarStock(int stock, int cantidad);
	
}
