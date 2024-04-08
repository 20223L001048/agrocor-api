package com.pruebas.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pruebas.exception.ResourceNotFoundException;
import com.pruebas.model.Producto;
import com.pruebas.repository.ProductoRepository;

@RestController
@RequestMapping("/api")
public class ProductoController {
	@Autowired
	private ProductoRepository productoRepository;
	
	@GetMapping("/productos")
		public List<Producto> ImprimirListaUsuarios(){
		return productoRepository.findAll();
	}
	
	@GetMapping("/productos/{id}")
	public ResponseEntity<Producto> buscarProductoId(@PathVariable(value = "id") Long idProducto) 		throws ResourceNotFoundException {
			Producto producto = productoRepository.findById(idProducto).orElseThrow(() ->
				new ResourceNotFoundException("No se encontró un producto para el id ::" + idProducto));
			return ResponseEntity.ok().body(producto);
	}
	
	@PostMapping("/productos")
	public Producto agregarProducto(@RequestBody Producto producto) {
		return productoRepository.save(producto);
	}
	
	@PutMapping("/productos/{id}")
	public ResponseEntity<Producto> actualizarProducto(@PathVariable(value = "id")
	Long idProducto, @RequestBody Producto datosProducto)
	throws ResourceNotFoundException{
		Producto producto = productoRepository.findById(idProducto).orElseThrow(() ->
		new ResourceNotFoundException("No se encontró un producto para el id :: " + idProducto));
		producto.setProducto(datosProducto.getProducto());
		producto.setCodigo(datosProducto.getCodigo());
		producto.setMarca(datosProducto.getMarca());
		producto.setPresentacion(datosProducto.getMarca());
		producto.setCategoria(datosProducto.getCategoria());
		producto.setPrecio(datosProducto.getPrecio());
		
		final Producto productoActualizado = productoRepository.save(producto);
		return ResponseEntity.ok(productoActualizado);
	}
	
	@DeleteMapping("/productos/{id}")
	public Map<String, Boolean> eliminarProducto(@PathVariable(value = "id") Long idProducto) throws ResourceNotFoundException{
		Producto producto = productoRepository.findById(idProducto).orElseThrow(() ->
		new ResourceNotFoundException("No se encontró un producto para el id :: " + idProducto));
		
		productoRepository.delete(producto);
		Map<String, Boolean> response = new HashMap<>();
		response.put("eliminado", Boolean.TRUE);
		return response;
	}
}
