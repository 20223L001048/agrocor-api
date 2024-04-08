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
import com.pruebas.model.Usuario;
import com.pruebas.repository.UsuarioRepository;

@RestController
@RequestMapping("/api")
public class UsuarioController {
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@GetMapping("/usuarios")
		public List<Usuario> ImprimirListaUsuarios(){
		return usuarioRepository.findAll();
	}
	
	@GetMapping("/usuarios/{id}")
	public ResponseEntity<Usuario> buscarUsuarioId(@PathVariable(value = "id") Long idUsuario) 		throws ResourceNotFoundException {
			Usuario usuario = usuarioRepository.findById(idUsuario).orElseThrow(() ->
				new ResourceNotFoundException("No se encontró un usuario para el id ::" + idUsuario));
			return ResponseEntity.ok().body(usuario);
	}
	
	@PostMapping("/usuarios")
	public Usuario agregarUsuario(@RequestBody Usuario usuario) {
		return usuarioRepository.save(usuario);
	}
	
	@PutMapping("/usuarios/{id}")
	public ResponseEntity<Usuario> actualizarUsuario(@PathVariable(value = "id")
	Long idUsuario, @RequestBody Usuario datosUsuario)
	throws ResourceNotFoundException{
		Usuario usuario = usuarioRepository.findById(idUsuario).orElseThrow(() ->
		new ResourceNotFoundException("No se encontró un usuario para el id :: " + idUsuario));
		usuario.setNombre(datosUsuario.getNombre());
		usuario.setApellidos(datosUsuario.getApellidos());
		usuario.setCorreo(datosUsuario.getCorreo());
		usuario.setContrasenia(datosUsuario.getContrasenia());
		
		final Usuario usuarioActualizado = usuarioRepository.save(usuario);
		return ResponseEntity.ok(usuarioActualizado);
	}
	
	@DeleteMapping("/usuarios/{id}")
	public Map<String, Boolean> eliminarUsuario(@PathVariable(value = "id") Long idUsuario) throws ResourceNotFoundException{
		Usuario usuario = usuarioRepository.findById(idUsuario).orElseThrow(() ->
		new ResourceNotFoundException("No se encontró un usuario para el id :: " + idUsuario));
		
		usuarioRepository.delete(usuario);
		Map<String, Boolean> response = new HashMap<>();
		response.put("eliminado", Boolean.TRUE);
		return response;
	}
}