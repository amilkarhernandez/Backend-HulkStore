package com.hulk.store.backend.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "facturas") //Utilizamos el @Table para cambiar el nombre de la tabla
public class Factura implements Serializable {
	
	//Atributos de la clase Factura, implementamos la clase serializable, ademas utilizamos decoradore
	//como @Id, @@GeneratedValue que nos permite generar el consecutivo, y el @NotNull

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "fecha")
	@Temporal(TemporalType.DATE)
	private Date fecha;

	//En este caso ignoramos las facturas de los clientes, para no crear el loop infinito
	
	@JsonIgnoreProperties({"facturas","hibernateLazyInitializer", "handler"})
	@ManyToOne(fetch = FetchType.LAZY)
	private Cliente cliente;

	//Creamos una relacion hacia los items de la facturas.
	
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "factura_id")
	private List<ItemFactura> items;

	public Factura() {
		items = new ArrayList<>();
	}

	//lo utilizamos con el decorador @PrePersist para crear la fecha automaticamente y asignarla al atributo fecha
	
	@PrePersist
	public void PreGuardarFecha() {
		this.fecha = new Date();
	}

	//Se crean los getters y setters
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<ItemFactura> getItems() {
		return items;
	}

	public void setItems(List<ItemFactura> items) {
		this.items = items;
	}
	
	//creamos un metodo para obtener el valor total de la factura, recorriendo cada item
	
	public Double getTotal(){
		Double total = 0.0;
		for(ItemFactura item: items) {
			total += item.getImporte(); 
		}
		return total;
	}
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
