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
import com.pruebas.model.Empleado;
import com.pruebas.repository.EmpleadoRepository;


@RestController
@RequestMapping("/api")

public class EmpleadoController {
	
	@Autowired
	private EmpleadoRepository empleadoRepository;
	
	@GetMapping("/empleados")
		public List<Empleado> ImprimirListaEmpleados(){
		return empleadoRepository.findAll();
	}
	
	@GetMapping("/empleados/{id}")
	public ResponseEntity<Empleado> buscarEmpleadoId(@PathVariable(value = "id") Long idEmpleado) 		throws ResourceNotFoundException {
			Empleado empleado = empleadoRepository.findById(idEmpleado).orElseThrow(() ->
				new ResourceNotFoundException("No se encontró un empleado para el id ::" + idEmpleado));
			return ResponseEntity.ok().body(empleado);
	}
	
	@PostMapping("/empleados")
	public Empleado agregarEmpleado(@RequestBody Empleado empleado) {
		return empleadoRepository.save(empleado);
	}
	
	@PutMapping("/empleados/{id}")
	public ResponseEntity<Empleado> actualizarEmpleado(@PathVariable(value = "id")
	Long idEmpleado, @RequestBody Empleado datosEmpleado)
	throws ResourceNotFoundException{
		Empleado empleado = empleadoRepository.findById(idEmpleado).orElseThrow(() ->
		new ResourceNotFoundException("No se encontró un empleado para el id :: " + idEmpleado));
		empleado.setNombre(datosEmpleado.getNombre());
		empleado.setApellidop(datosEmpleado.getApellidop());
		empleado.setApellidom(datosEmpleado.getApellidom());
		empleado.setUsuario(datosEmpleado.getUsuario());
				
		final Empleado empleadoActualizado = empleadoRepository.save(empleado);
		return ResponseEntity.ok(empleadoActualizado);
	}
	
	@DeleteMapping("/empleados/{id}")
	public Map<String, Boolean> eliminarEmpleado(@PathVariable(value = "id") Long idEmpleado) throws ResourceNotFoundException{
		Empleado empleado = empleadoRepository.findById(idEmpleado).orElseThrow(() ->
		new ResourceNotFoundException("No se encontró un empleado para el id :: " + idEmpleado));
		
		empleadoRepository.delete(empleado);
		Map<String, Boolean> response = new HashMap<>();
		response.put("eliminado", Boolean.TRUE);
		return response;
	}

}
