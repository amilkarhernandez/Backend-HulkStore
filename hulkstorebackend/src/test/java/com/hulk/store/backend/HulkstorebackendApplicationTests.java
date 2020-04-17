package com.hulk.store.backend;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.hulk.store.backend.dao.ICategoriaDao;
import com.hulk.store.backend.dao.IClienteDao;
import com.hulk.store.backend.dao.IProductoDao;
import com.hulk.store.backend.entity.Categoria;
import com.hulk.store.backend.entity.Cliente;
import com.hulk.store.backend.entity.Producto;
import com.hulk.store.backend.service.CategoriaServiceImp;
import com.hulk.store.backend.service.ClienteServiceImp;
import com.hulk.store.backend.service.ProductoServiceImp;

//utilizamos esta anotacion para utilizarlo al correr los tests con Spring
@RunWith(SpringRunner.class)
@SpringBootTest
public class HulkstorebackendApplicationTests {

	//Inyectamos las dependencias
	@Autowired
	private CategoriaServiceImp categoriaServiceImp;

	//lo utilizamos para hacer el simulacro del dao, objeto de acceso a datos
	@MockBean
	private ICategoriaDao categoriaDao;
	
	@Autowired
	private ClienteServiceImp clienteService;
	
	@MockBean
	private IClienteDao clienteDao;
	
	@Autowired
	private ProductoServiceImp productoService;
	
	@MockBean
	private IProductoDao productoDao;
	
	//Creamos el test para obtener el listado
	@Test
	public void getCategoriasTest() {
		
		Categoria categoria = new Categoria();
		categoria.setId(Long.valueOf("1"));
		categoria.setDescripcion("Nueva Descripcoin");
		
		Categoria categoria2 = new Categoria();
		categoria2.setId(Long.valueOf("2"));
		categoria2.setDescripcion("Nueva Descripcoin2");
		
		when(categoriaDao.findAll()).thenReturn(Stream
				.of(categoria, categoria2).collect(Collectors.toList()));
	}

	//Creamos un test para Guardar
	@Test
	public void saveCategoriaTest() {
		Categoria categoria = new Categoria();
		categoria.setId(Long.valueOf("1"));
		categoria.setDescripcion("Nueva Descripcoin");

		when(categoriaDao.save(categoria)).thenReturn(categoria);
		assertEquals(categoria, categoriaServiceImp.save(categoria));

	}
	
	//Test para Eliminar
	@Test
	public void deleteCategoriaByIdTest() {
		
		Categoria categoria = new Categoria();
		categoria.setId(Long.valueOf("1"));
		categoria.setDescripcion("Nueva Descripcoin");
		
		categoriaServiceImp.delete(categoria.getId());
		verify(categoriaDao, times(1)).deleteById((categoria.getId()));

	}
	
	//Creamos el test para obtener el listado
	@Test
	public void getClientesTest() {
		
		Cliente cliente = new Cliente();
		cliente.setId(Long.valueOf("1"));
		cliente.setNit("12345");
		cliente.setNombres("Amilkar J");
		cliente.setApellidos("Hernandez");
		cliente.setTelefono("462392837");
		cliente.setGenero("Masculino");
		
		Cliente cliente2 = new Cliente();
		cliente2.setId(Long.valueOf("2"));
		cliente2.setNit("98866");
		cliente2.setNombres("Andres J");
		cliente2.setApellidos("Julians");
		cliente2.setTelefono("0937734");
		cliente2.setGenero("Masculino");
		
		when(clienteDao.findAll()).thenReturn(Stream
				.of(cliente, cliente2).collect(Collectors.toList()));
	}
	
	//Creamos un test para Guardar
	@Test
	public void saveClienteTest() {
		Cliente cliente = new Cliente();
		cliente.setId(Long.valueOf("1"));
		cliente.setNit("12345");
		cliente.setNombres("Amilkar J");
		cliente.setApellidos("Hernandez");
		cliente.setTelefono("462392837");
		cliente.setGenero("Masculino");

		when(clienteDao.save(cliente)).thenReturn(cliente);
		assertEquals(cliente, clienteService.save(cliente));

	}
	
	@Test
	public void deleteCLienteByIdTest() {
		
		Cliente cliente = new Cliente();
		cliente.setId(Long.valueOf("1"));
		cliente.setNit("12345");
		cliente.setNombres("Amilkar J");
		cliente.setApellidos("Hernandez");
		cliente.setTelefono("462392837");
		cliente.setGenero("Masculino");
		
		clienteService.delete(cliente.getId());
		verify(clienteDao, times(1)).deleteById((cliente.getId()));

	}
	
	//Creamos el test para obtener el listado
	@Test
	public void getProductoTest() {
		
		Categoria categoria = new Categoria();
		categoria.setId(Long.valueOf("1"));
		categoria.setDescripcion("Vasos");
		
		Producto producto = new Producto();
		producto.setId(Long.valueOf("1"));
		producto.setSerial("0083");
		producto.setDescripcion("Vasos hulk");
		producto.setStock(20);
		producto.setValorUnitario(23000.0);
		producto.setCategoria(categoria);
		
		
		Producto producto2 = new Producto();
		producto2.setId(Long.valueOf("1"));
		producto2.setSerial("0087");
		producto2.setDescripcion("Vasos Pequ");
		producto2.setStock(20);
		producto2.setValorUnitario(2000.0);
		producto2.setCategoria(categoria);
		
		when(productoDao.findAll()).thenReturn(Stream
				.of(producto, producto2).collect(Collectors.toList()));
	}
	
	//Creamos un test para Guardar
	@Test
	public void saveProductoTest() {
		Categoria categoria = new Categoria();
		categoria.setId(Long.valueOf("1"));
		categoria.setDescripcion("Vasos");
		
		Producto producto = new Producto();
		producto.setId(Long.valueOf("1"));
		producto.setSerial("0083");
		producto.setDescripcion("Vasos hulk");
		producto.setStock(20);
		producto.setValorUnitario(23000.0);
		producto.setCategoria(categoria);

		when(productoDao.save(producto)).thenReturn(producto);
		assertEquals(producto, productoService.save(producto));

	}
	
	//Creamos un test para Eliminar y verificamos
	@Test
	public void deleteProductoByIdTest() {
		
		Producto producto = new Producto();
		producto.setId(Long.valueOf("1"));
		producto.setSerial("0083");
		producto.setDescripcion("Vasos hulk");
		producto.setStock(20);
		producto.setValorUnitario(23000.0);
		
		productoService.delete(producto.getId());
		verify(productoDao, times(1)).deleteById((producto.getId()));

	}

}
