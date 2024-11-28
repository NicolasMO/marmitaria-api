package br.com.marmitaria.service.endereco;

import br.com.marmitaria.dto.endereco.CadastrarEnderecoDTO;
import br.com.marmitaria.entity.endereco.Endereco;

public interface EnderecoService {
	Endereco cadastrarEndereco(CadastrarEnderecoDTO EnderecoDTO);
}
