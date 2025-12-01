package br.com.marmitaria.service.endereco;

import br.com.marmitaria.dto.endereco.CadastroEnderecoDTO;
import br.com.marmitaria.dto.endereco.RespostaEnderecoDTO;

import java.util.List;

public interface EnderecoService {
	RespostaEnderecoDTO cadastrarEndereco(CadastroEnderecoDTO dto);
    List<RespostaEnderecoDTO> listarEnderecosDoUsuario();
    RespostaEnderecoDTO listarEnderecoPorID(Long id);
    void removerEnderecoDoUsuario(Long id);
}
