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
import com.pruebas.model.Proveedor;
import com.pruebas.repository.ProveedorRepository;


@RestController
@RequestMapping("/api")

public class ProveedorController {
	
	@Autowired
	private ProveedorRepository proveedorRepository;
	
	@GetMapping("/proveedores")
		public List<Proveedor> ImprimirListaProveedores(){
		return proveedorRepository.findAll();
	}
	//
	@GetMapping("/proveedores/{id}")
	public ResponseEntity<Proveedor> buscarProveedorId(@PathVariable(value = "id") Long idProveedor) 		throws ResourceNotFoundException {
			Proveedor proveedor = proveedorRepository.findById(idProveedor).orElseThrow(() ->
				new ResourceNotFoundException("No se encontró un proveedor para el id ::" + idProveedor));
			return ResponseEntity.ok().body(proveedor);
	}
	
	@PostMapping("/proveedores")
	public Proveedor agregarProveedor(@RequestBody Proveedor proveedor) {
		return proveedorRepository.save(proveedor);
	}
	
	@PutMapping("/proveedores/{id}")
	public ResponseEntity<Proveedor> actualizarProveedor(@PathVariable(value = "id")
	Long idProveedor, @RequestBody Proveedor datosProveedor)
	throws ResourceNotFoundException{
		Proveedor proveedor = proveedorRepository.findById(idProveedor).orElseThrow(() ->
		new ResourceNotFoundException("No se encontró un proveedor para el id :: " + idProveedor));
		proveedor.setProducto(datosProveedor.getProducto());
		proveedor.setNombre(datosProveedor.getNombre());
		proveedor.setApellidop(datosProveedor.getApellidop());
		proveedor.setApellidom(datosProveedor.getApellidom());
		proveedor.setCorreo(datosProveedor.getCorreo());
		proveedor.setTelefono(datosProveedor.getTelefono());
		proveedor.setEmpresa(datosProveedor.getEmpresa());
		
		final Proveedor proveedorActualizado = proveedorRepository.save(proveedor);
		return ResponseEntity.ok(proveedorActualizado);
	}
	
	@DeleteMapping("/proveedores/{id}")
	public Map<String, Boolean> eliminarProveedor(@PathVariable(value = "id") Long idProveedor) throws ResourceNotFoundException{
		Proveedor proveedor = proveedorRepository.findById(idProveedor).orElseThrow(() ->
		new ResourceNotFoundException("No se encontró un proveedor para el id :: " + idProveedor));
		
		proveedorRepository.delete(proveedor);
		Map<String, Boolean> response = new HashMap<>();
		response.put("eliminado", Boolean.TRUE);
		return response;
	}

}
