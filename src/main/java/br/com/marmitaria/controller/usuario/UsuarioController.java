package br.com.marmitaria.controller.usuario;

import java.util.List;
import java.util.Optional;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.marmitaria.dto.usuario.CadastroUsuarioDTO;
import br.com.marmitaria.entity.usuario.Usuario;
import br.com.marmitaria.service.usuario.UsuarioService;

@RestController
@RequestMapping("usuario")
public class UsuarioController {

    @Autowired
	private UsuarioService usuarioService;
	
	@GetMapping
	public ResponseEntity<?> buscarTodos() {
		List<Usuario> usuarios = usuarioService.listarTodos();
		return ResponseEntity.ok(usuarios);
	}

    @GetMapping("/info")
    public ResponseEntity<Optional<Usuario>> buscarUsuario() {
		Optional<Usuario> usuario = usuarioService.buscarUsuario();
		return ResponseEntity.status(HttpStatus.OK).body(usuario);
	}
	
	@PostMapping
	public ResponseEntity<Usuario> cadastrarUsuario(@Valid @RequestBody CadastroUsuarioDTO cadastroUsuarioDTO) {
		Usuario criado = usuarioService.cadastrarUsuario(cadastroUsuarioDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(criado);
	}

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerUsuario(@PathVariable long id) {
        usuarioService.removerUsuario(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
