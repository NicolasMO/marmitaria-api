package br.com.marmitaria.controller.usuario;

import java.util.List;
import java.util.Optional;

import br.com.marmitaria.dto.usuario.RespostaUsuarioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.marmitaria.entity.usuario.Usuario;
import br.com.marmitaria.service.usuario.UsuarioService;

@RestController
@RequestMapping("usuarios")
public class UsuarioController {

    @Autowired
	private UsuarioService usuarioService;
	
	@GetMapping
	public ResponseEntity<List<RespostaUsuarioDTO>> buscarTodos() {
		List<RespostaUsuarioDTO> dto = usuarioService.listarTodos();
		return ResponseEntity.status(HttpStatus.OK).body(dto);
	}

    @GetMapping("/info")
    public ResponseEntity<RespostaUsuarioDTO> buscarUsuario() {
        RespostaUsuarioDTO dto = usuarioService.buscarUsuario();
		return ResponseEntity.status(HttpStatus.OK).body(dto);
	}

    @GetMapping("/{id}")
    public ResponseEntity<RespostaUsuarioDTO> buscarUsuarioPorID(@PathVariable Long id) {
        RespostaUsuarioDTO dto = usuarioService.buscarUsuarioPorID(id);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerUsuario(@PathVariable long id) {
        usuarioService.removerUsuario(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
