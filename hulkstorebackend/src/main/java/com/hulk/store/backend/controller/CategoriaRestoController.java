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

import com.hulk.store.backend.entity.Categoria;
import com.hulk.store.backend.service.ICategoriaService;

//Configuramos el CrossOrigin ya que es una solicitud http de origen cruzado, ya que quien lo va a consumir
//esta diferente al dominio.
@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api")
public class CategoriaRestoController {

	// Inyectamos la interfaz del servicio para utilizarlo
	@Autowired
	private ICategoriaService categoriaService;

	// Listado de Categorias
	@GetMapping("/categorias")
	public List<Categoria> index() {
		return categoriaService.findAll();
	}

	// buscar una categoria por ID
	@GetMapping("/categorias/{id}")
	public ResponseEntity<?> show(@PathVariable Long id) {

		Categoria categoria = null;
		Map<String, Object> response = new HashMap<>();

		try {
			categoria = categoriaService.findById(id);
		} catch (DataAccessException e) {
			// Si existe un error al insertar devolveremos el error al cliente.
			response.put("mensaje", "Error al Realizar la Consulta en la Base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		// si el id que estamos buscando no existe le informamos al cliente que esta
		// consumiento el servicio
		if (categoria == null) {
			response.put("mensaje",
					"La Categria con el Id:".concat(id.toString()).concat(" no existe en la base de datos"));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		// si todo sale bien, respondemos al cliente con la entidad buscada y enviamos
		// un status ok
		return new ResponseEntity<Categoria>(categoria, HttpStatus.OK);
	}

//		//Metodo para Guardar una Categoria
	@PostMapping("/categorias")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> create(@Valid @RequestBody Categoria categoria, BindingResult result) {
		Categoria categoriaNew = null;
		Map<String, Object> response = new HashMap<>();

		// si existen errores, los colocamos en una lista y enviamos.
		if (result.hasErrors()) {
			List<String> errors = categoriaService.validacion(result);
			
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}

		try {
			categoriaNew = categoriaService.save(categoria);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al Realizar el Insert en la Base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		// Se envia la respuesta del cliente
		response.put("mensaje", "La Categoria ha sido creado con Exito");
		response.put("categoria", categoriaNew);

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@PutMapping("/categorias/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody Categoria categoria, BindingResult result,
			@PathVariable Long id) {

		// Obtenemos por medio del servicio el id de la entidad
		Categoria categoriaActual = categoriaService.findById(id);
		Categoria categoriaUpdated = null;

		Map<String, Object> response = new HashMap<>();

		if (result.hasErrors()) {

			List<String> errors = categoriaService.validacion(result);

			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}

		if (categoriaActual == null) {
			response.put("mensaje", "Error: no se puede Editar, La categoria con el Id:".concat(id.toString())
					.concat(" no existe en la base de datos"));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		try {
			// Guardamos
			categoriaUpdated = categoriaService.save(categoriaService.Actualizar(categoriaActual, categoria));

		} catch (DataAccessException e) {
			// TODO: handle exception
			response.put("mensaje", "Error al Actualizar en la Base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "La categoria ha sido Actualizado con Exito");
		response.put("categoria", categoriaUpdated);

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	// ELIMINAR UN REGISTRO
	@DeleteMapping("/categorias/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();

		try {
			categoriaService.delete(id);
		} catch (DataAccessException e) {
			// TODO: handle exception
			response.put("mensaje", "Error al Eliminar en la Base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "La Categoria ha sido Eliminada con Exito");

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

	}
}
