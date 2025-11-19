package br.com.marmitaria.controller.usuario;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.marmitaria.dto.usuario.CadastrarUsuarioDTO;
import br.com.marmitaria.entity.usuario.Usuario;
import br.com.marmitaria.service.usuario.UsuarioService;

@RestController
@RequestMapping("usuario")
public class UsuarioController {

    @Autowired
	private UsuarioService usuarioService;
	
	@GetMapping
	public ResponseEntity<?> buscarTodos() {
		List<Usuario> usuarios = usuarioService.buscarTodos();
		return ResponseEntity.ok(usuarios);
	}
	
	@GetMapping(value = "{id}")
	public ResponseEntity<Optional<Usuario>> buscarUsuario(@PathVariable Long id) {
		Optional<Usuario> usuario = usuarioService.buscarUsuario(id);
		return ResponseEntity.ok(usuario);
	}
	
	@PostMapping
	public ResponseEntity<Usuario> cadastrarUsuario(@Valid @RequestBody CadastrarUsuarioDTO cadastrarUsuarioDTO) {
		Usuario criado = usuarioService.cadastrarUsuario(cadastrarUsuarioDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(criado);
	}

    @DeleteMapping("/{id}")
    public ResponseEntity<String> removerUsuario(@PathVariable long id) {
        usuarioService.removerUsuario(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Usuário removido com sucesso.");
    }
}
