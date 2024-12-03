package br.com.marmitaria.controller.endereco;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.marmitaria.dto.endereco.CadastrarEnderecoDTO;
import br.com.marmitaria.entity.endereco.Endereco;
import br.com.marmitaria.service.endereco.EnderecoService;

@RestController
@RequestMapping("endereco")
public class EnderecoController {
	
	private final EnderecoService enderecoService;
	
	public EnderecoController(EnderecoService enderecoService) {
		this.enderecoService = enderecoService;
	}
	
	//Endpoints
	
	@PostMapping
	public ResponseEntity<Endereco> cadastrarEndereco(@RequestBody CadastrarEnderecoDTO cadastrarEnderecoDTO) {
		enderecoService.cadastrarEndereco(cadastrarEnderecoDTO);
		return new ResponseEntity<Endereco>(HttpStatus.CREATED);
	}
}
