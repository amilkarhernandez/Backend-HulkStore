package com.hulk.store.backend.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "facturas_items")
public class ItemFactura implements Serializable {
	
	//Atributos de la clase ItemFactura, implementamos la clase serializable, ademas utilizamos decoradore
	//como @Id, @@GeneratedValue que nos permite generar el consecutivo, y el @NotNull

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Integer cantidad;

	//Creamos la relacion con producto para obtenerlos
	
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@ManyToOne(fetch = FetchType.LAZY)
	private Producto producto;

	//creamos los Setters y Getters
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	//Se crea un metodo para multiplicar la cantidad ingresada por el valor unitario del producto.
	
	public Double getImporte() {
		return this.cantidad.doubleValue() * producto.getValorUnitario();
	}
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
