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
import org.springframework.web.bind.annotation.*;

import br.com.marmitaria.service.usuario.UsuarioService;

@RequiredArgsConstructor
@RestController
@RequestMapping("usuarios")
public class UsuarioController {

	private final UsuarioService usuarioService;
    private final EnderecoService enderecoService;
	
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
    public ResponseEntity<Void> removerUsuario(@PathVariable Long id) {
        usuarioService.removerUsuario(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/me/endereco/buscar")
    public ResponseEntity<List<RespostaEnderecoDTO>> listarEnderecosDoUsuario() {
        List<RespostaEnderecoDTO> endereco = enderecoService.listarEnderecosDoUsuario();
        return ResponseEntity.status(HttpStatus.OK).body(endereco);
    }

    @GetMapping("/me/endereco/{id}")
    public ResponseEntity<RespostaEnderecoDTO> listarEnderecoDoUsuarioPorID(@PathVariable Long id) {
        RespostaEnderecoDTO endereco = enderecoService.listarEnderecoPorID(id);
        return ResponseEntity.status(HttpStatus.OK).body(endereco);
    }

    @PostMapping("/me/endereco")
    public ResponseEntity<RespostaEnderecoDTO> cadastrarEndereco(@Valid @RequestBody CadastroEnderecoDTO dto) {
        RespostaEnderecoDTO endereco = enderecoService.cadastrarEndereco(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(endereco);
    }

    @DeleteMapping("/me/endereco/{id}")
    public ResponseEntity<Void> removerEndereco(@PathVariable Long id) {
        enderecoService.removerEnderecoDoUsuario(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
