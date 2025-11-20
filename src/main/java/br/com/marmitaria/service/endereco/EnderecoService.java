package br.com.marmitaria.service.endereco;

import br.com.marmitaria.dto.endereco.CadastroEnderecoDTO;
import br.com.marmitaria.dto.endereco.RespostaEnderecoDTO;
import br.com.marmitaria.entity.endereco.Endereco;

import java.util.List;

public interface EnderecoService {
	Endereco cadastrarEndereco(Long usuarioId, CadastroEnderecoDTO dto);
    List<RespostaEnderecoDTO> listarEnderecosDoUsuario(Long usuarioId);
    void removerEnderecoDoUsuario(long usuarioId, long id);
}
