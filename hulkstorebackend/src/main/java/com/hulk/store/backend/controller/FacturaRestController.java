package com.hulk.store.backend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.hulk.store.backend.entity.Factura;
import com.hulk.store.backend.entity.ItemFactura;
import com.hulk.store.backend.entity.Producto;
import com.hulk.store.backend.service.IClienteService;
import com.hulk.store.backend.service.IProductoService;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api")
public class FacturaRestController {

	// importamos el clienteService para tener sus metodos y no anotamos con el
	// decorador Autowired
	@Autowired
	private IClienteService clienteService;

	@Autowired
	private IProductoService productoService;

	// Obtener una factura
	@GetMapping("/facturas/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Factura show(@PathVariable Long id) {
		return clienteService.findFacturaById(id);
	}

	// Eliminar Facturas
	@DeleteMapping("/facturas/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		clienteService.deleteFacturaById(id);
	}

	// Filtramos los productos por el nombre que escribimos en el PathVariable
	@GetMapping("/facturas/filtrarproductos/{term}")
	@ResponseStatus(HttpStatus.OK)
	public List<Producto> filtrarProductos(@PathVariable String term) {
		return clienteService.findProductoByNombre(term);
	}

	// Crear una factura
	@PostMapping("/facturas")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> crear(@RequestBody Factura factura) {
		Factura fact = factura;
		Map<String, Object> response = new HashMap<>();

		// Recorremos los items para obtener la informacion ingresada
		for (ItemFactura item : fact.getItems()) {
			// Obtenemos el producto el cual vamos a ingresar
			Producto proMod = productoService.findById(item.getProducto().getId());
			// si el producto buscado, en su estock es cero, no podemos hacer la venta, y
			// devolver una respuesta
			if (proMod.getStock() == 0) {
				response.put("mensaje",
						"Error: El Producto ".concat(proMod.getDescripcion()).concat(" Tiene un Stock en Cero."));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			// si la cantidad ingresada es mayor a la que esta en el stock, entonces
			// devolvemos una respuesta.
			if (item.getCantidad() > proMod.getStock()) {
				response.put("mensaje", "Error: El Producto ".concat(proMod.getDescripcion())
						.concat(" No Cuenta con la Cantidad Ingresada"));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			// actualizamos el stock restando la cantdad al stock de ese producto
			proMod.setStock(clienteService.restarStock(proMod.getStock(), item.getCantidad()));
			// guardamos el Producto y la Factura
			productoService.save(proMod);

		}
		clienteService.saveFactura(factura);

		response.put("mensaje", "La Factura ha sido creado con Exito");
		response.put("factura", fact);
		// return clienteService.saveFactura(factura);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
}
