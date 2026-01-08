package br.com.marmitaria.controller.usuario;

import java.util.List;

import br.com.marmitaria.dto.endereco.CadastroEnderecoDTO;
import br.com.marmitaria.dto.endereco.RespostaEnderecoDTO;
import br.com.marmitaria.dto.usuario.RespostaUsuarioDTO;
import br.com.marmitaria.service.endereco.EnderecoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import br.com.marmitaria.service.usuario.UsuarioService;

@RequiredArgsConstructor
@RestController
@RequestMapping("usuarios")
public class UsuarioController {

	private final UsuarioService usuarioService;
    private final EnderecoService enderecoService;

    @PreAuthorize("hasAnyRoles('ADMIN', 'OPERADOR')")
	@GetMapping
	public ResponseEntity<List<RespostaUsuarioDTO>> buscarTodos() {
		List<RespostaUsuarioDTO> dto = usuarioService.listarTodos();
		return ResponseEntity.status(HttpStatus.OK).body(dto);
	}

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/info")
    public ResponseEntity<RespostaUsuarioDTO> buscarUsuarioAutenticado() {
        RespostaUsuarioDTO dto = usuarioService.buscarUsuarioAutenticado();
		return ResponseEntity.status(HttpStatus.OK).body(dto);
	}

    @PreAuthorize("hasAnyRoles('ADMIN', 'OPERADOR')")
    @GetMapping("/{id}")
    public ResponseEntity<RespostaUsuarioDTO> buscarUsuarioPorID(@PathVariable Long id) {
        RespostaUsuarioDTO dto = usuarioService.buscarUsuarioPorID(id);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @PreAuthorize("hasAnyRoles('ADMIN', 'OPERADOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerUsuario(@PathVariable Long id) {
        usuarioService.removerUsuario(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/me/endereco/buscar")
    public ResponseEntity<List<RespostaEnderecoDTO>> listarEnderecosDoUsuario() {
        List<RespostaEnderecoDTO> endereco = enderecoService.listarEnderecosDoUsuario();
        return ResponseEntity.status(HttpStatus.OK).body(endereco);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/me/endereco/{id}")
    public ResponseEntity<RespostaEnderecoDTO> listarEnderecoDoUsuarioPorID(@PathVariable Long id) {
        RespostaEnderecoDTO endereco = enderecoService.listarEnderecoPorID(id);
        return ResponseEntity.status(HttpStatus.OK).body(endereco);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/me/endereco")
    public ResponseEntity<RespostaEnderecoDTO> cadastrarEndereco(@Valid @RequestBody CadastroEnderecoDTO dto) {
        RespostaEnderecoDTO endereco = enderecoService.cadastrarEndereco(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(endereco);
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/me/endereco/{id}")
    public ResponseEntity<Void> removerEndereco(@PathVariable Long id) {
        enderecoService.removerEnderecoDoUsuario(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
