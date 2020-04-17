package com.hulk.store.backend.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class Cliente implements Serializable {
	
	//Atributos de la clase Cliente, implementamos la clase serializable, ademas utilizamos decoradore
	//como @Id, @@GeneratedValue que nos permite generar el consecutivo, y el @NotNull

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull(message = "No puede Ser vacio")
	@Column(unique = true)
	private String nit;

	@NotNull(message = "No puede Ser vacio")
	private String nombres;

	@NotNull(message = "No puede Ser vacio")
	private String apellidos;

	@NotNull(message = "No puede Ser vacio")
	private String telefono;

	@NotNull(message = "No puede Ser vacio")
	private String genero;

	//Se crea una relacion, haciendo una lista de Facturas, para visualizar las facturas que tiene este cliente.
	//utilizamos el @JsonIgnoreProperties para que no nos muestre atributos de Json, en este caso quitamos que nos muestre
	//el cliente ya que nos crea un loop infinito buscando las facturas, luego los clientes y asi sicesivamente

	@JsonIgnoreProperties({"cliente","hibernateLazyInitializer", "handler"})
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "cliente", cascade = CascadeType.ALL)
	private List<Factura> facturas;
	
	
	public Cliente() {
		this.facturas = new ArrayList<Factura>();
	}
	

	//Se crean los setters y getters

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNit() {
		return nit;
	}

	public void setNit(String nit) {
		this.nit = nit;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public List<Factura> getFacturas() {
		return facturas;
	}

	public void setFacturas(List<Factura> facturas) {
		this.facturas = facturas;
	}
	
	

	public Cliente(Long id, @NotNull(message = "No puede Ser vacio") String nit,
			@NotNull(message = "No puede Ser vacio") String nombres,
			@NotNull(message = "No puede Ser vacio") String apellidos,
			@NotNull(message = "No puede Ser vacio") String telefono,
			@NotNull(message = "No puede Ser vacio") String genero) {
		super();
		this.id = id;
		this.nit = nit;
		this.nombres = nombres;
		this.apellidos = apellidos;
		this.telefono = telefono;
		this.genero = genero;
	}



	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
