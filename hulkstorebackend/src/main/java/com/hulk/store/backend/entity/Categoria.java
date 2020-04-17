package com.hulk.store.backend.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Categoria implements Serializable {

	//Atributos de la clase Categoria, implementamos la clase serializable, ademas utilizamos decoradore
	//como @Id, @@GeneratedValue que nos permite generar el consecutivo, y el @NotNull
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull(message = "No puede Ser vacio")
	private String descripcion;

	//Se Generan los Getters y Settes

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
