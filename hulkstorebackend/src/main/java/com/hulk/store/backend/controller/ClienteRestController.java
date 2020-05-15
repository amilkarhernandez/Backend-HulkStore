package com.hulk.store.backend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.hulk.store.backend.entity.Cliente;
import com.hulk.store.backend.service.IClienteService;

//Configuramos el CrossOrigin ya que es una solicitud http de origen cruzado, ya que quien lo va a consumir
//esta diferente al dominio.
@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api")
public class ClienteRestController {
	
	//Inyectamos la interfaz del servicio para utilizarlo
	@Autowired
	private IClienteService clienteService;
	
	//Listado de Clientes
		@GetMapping("/clientes")
		public List<Cliente> index() {
			return clienteService.finAll();
		}
		
		//buscar un Cliente por ID
		@GetMapping("/clientes/{id}")
		public ResponseEntity<?> show(@PathVariable Long id) {

			Cliente cliente = null;
			Map<String, Object> response = new HashMap<>();

			try {
				cliente = clienteService.findById(id);
			} catch (DataAccessException e) {
				// Si existe un error al insertar devolveremos el error al cliente.
				response.put("mensaje", "Error al Realizar la Consulta en la Base de datos");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}

			if (cliente == null) {
				response.put("mensaje",
						"El Cliente con el Id:".concat(id.toString()).concat(" no existe en la base de datos"));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}

			return new ResponseEntity<Cliente>(cliente, HttpStatus.OK);
		}
		
//		//Metodo para Guardar un Cliente
		@PostMapping("/clientes")
		@ResponseStatus(HttpStatus.CREATED)
		public ResponseEntity<?> create(@Valid @RequestBody Cliente cliente, BindingResult result) {
			Cliente clienteNew = null;
			Map<String, Object> response = new HashMap<>();

			if (result.hasErrors()) {
				List<String> errors = clienteService.validacion(result);

				response.put("errors", errors);
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
			}

			try {
				clienteNew = clienteService.save(cliente);
			} catch (DataAccessException e) {
				response.put("mensaje", "Error al Realizar el Insert en la Base de datos");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.put("mensaje", "El Cliente ha sido creado con Exito");
			response.put("cliente", clienteNew);

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
		}
		
		@PutMapping("/clientes/{id}")
		public ResponseEntity<?> update(@Valid @RequestBody Cliente cliente, BindingResult result,
				@PathVariable Long id) {

			Cliente clienteActual = clienteService.findById(id);
			Cliente clienteUpdated = null;

			Map<String, Object> response = new HashMap<>();

			if (result.hasErrors()) {
				List<String> errors = clienteService.validacion(result);

				response.put("errors", errors);
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
			}
			if (clienteActual == null) {
				response.put("mensaje", "Error: no se puede Editar, El Cliente con el Id:".concat(id.toString())
						.concat(" no existe en la base de datos"));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}

			try {
				clienteUpdated = clienteService.save(clienteService.Actualizar(clienteActual, cliente));
			} catch (DataAccessException e) {
				response.put("mensaje", "Error al Actualizar en la Base de datos");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.put("mensaje", "El Cliente ha sido Actualizado con Exito");
			response.put("cliente", clienteUpdated);

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
		}

		// ELIMINAR UN REGISTRO
		@DeleteMapping("/clientes/{id}")
		public ResponseEntity<?> delete(@PathVariable Long id) {
			Map<String, Object> response = new HashMap<>();

			try {
				clienteService.delete(id);
			} catch (DataAccessException e) {
				response.put("mensaje", "Error al Eliminar en la Base de datos");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.put("mensaje", "El Cliente ha sido Eliminado con Exito");

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

		}

}
