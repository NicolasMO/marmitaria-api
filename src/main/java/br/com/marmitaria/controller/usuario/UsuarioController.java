package br.com.marmitaria.controller.usuario;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.marmitaria.dto.usuario.CadastrarUsuarioDTO;
import br.com.marmitaria.entity.usuario.Usuario;
import br.com.marmitaria.service.usuario.UsuarioService;

@RestController
@RequestMapping("usuario")
public class UsuarioController {

	private final UsuarioService usuarioService;
	
	public UsuarioController(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}
	
	//Endpoints
	
	@GetMapping
	public ResponseEntity<?> buscarTodos() {
		List<Usuario> usuarios = usuarioService.buscarTodos();
		return ResponseEntity.ok(usuarios);
	}
	
	@GetMapping(value = "{id}")
	public ResponseEntity<?> buscarUsuario(@PathVariable Long id) {
		Optional<Usuario> usuario = usuarioService.buscarUsuario(id);
		return ResponseEntity.ok(usuario);
	}
	
	@PostMapping
	public ResponseEntity<Usuario> cadastrarUsuario(@RequestBody CadastrarUsuarioDTO cadastrarUsuarioDTO) {
		Usuario usuario = usuarioService.cadastrarUsuario(cadastrarUsuarioDTO);
		System.out.println(usuario);
		return new ResponseEntity<Usuario>(usuario, HttpStatus.CREATED);
	}
}
