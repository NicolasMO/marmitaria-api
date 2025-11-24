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
@RequestMapping("enderecos")
public class EnderecoController {
	
	@Autowired
    EnderecoService enderecoService;

    @GetMapping("/buscar")
    public ResponseEntity<List<RespostaEnderecoDTO>> listarEnderecosDoUsuario() {
        List<RespostaEnderecoDTO> endereco = enderecoService.listarEnderecosDoUsuario();
        return ResponseEntity.status(HttpStatus.OK).body(endereco);
    }

	@PostMapping
	public ResponseEntity<Endereco> cadastrarEndereco(@Valid @RequestBody CadastroEnderecoDTO dto) {
		Endereco endereco = enderecoService.cadastrarEndereco(dto);
		return ResponseEntity.status(HttpStatus.CREATED).body(endereco);
	}

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerEndereco(@PathVariable Long id) {
        enderecoService.removerEnderecoDoUsuario(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
