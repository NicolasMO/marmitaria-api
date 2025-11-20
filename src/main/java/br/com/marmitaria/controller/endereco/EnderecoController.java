package br.com.marmitaria.controller.endereco;

import br.com.marmitaria.dto.endereco.RespostaEnderecoDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.marmitaria.dto.endereco.CadastroEnderecoDTO;
import br.com.marmitaria.entity.endereco.Endereco;
import br.com.marmitaria.service.endereco.EnderecoService;

import java.util.List;

@RestController
@RequestMapping("endereco")
public class EnderecoController {
	
	@Autowired
    EnderecoService enderecoService;

    @GetMapping("/{id}")
    public ResponseEntity<List<RespostaEnderecoDTO>> listarEnderecosDoUsuario(@PathVariable long id) {
        List<RespostaEnderecoDTO> endereco = enderecoService.listarEnderecosDoUsuario(id);
        return ResponseEntity.status(HttpStatus.OK).body(endereco);
    }

	@PostMapping("/{id}")
	public ResponseEntity<Endereco> cadastrarEndereco(@PathVariable long id, @Valid @RequestBody CadastroEnderecoDTO dto) {
		Endereco endereco = enderecoService.cadastrarEndereco(id, dto);
		return ResponseEntity.status(HttpStatus.CREATED).body(endereco);
	}

    @DeleteMapping("/{usuarioId}/{id}")
    public ResponseEntity<String> removerEndereco(@PathVariable long usuarioId, @PathVariable long id) {
        enderecoService.removerEnderecoDoUsuario(usuarioId, id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Endereço removido com sucesso");
    }
}
