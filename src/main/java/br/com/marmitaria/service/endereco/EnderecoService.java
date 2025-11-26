package br.com.marmitaria.service.endereco;

import br.com.marmitaria.dto.endereco.CadastroEnderecoDTO;
import br.com.marmitaria.dto.endereco.RespostaEnderecoDTO;

import java.util.List;

public interface EnderecoService {
	RespostaEnderecoDTO cadastrarEndereco(CadastroEnderecoDTO dto);
    List<RespostaEnderecoDTO> listarEnderecosDoUsuario();
    RespostaEnderecoDTO listarEnderecoDoUsuarioPorID(Long id);
    void removerEnderecoDoUsuario(Long id);
}
