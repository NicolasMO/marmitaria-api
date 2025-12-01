package br.com.marmitaria.service.endereco;

import br.com.marmitaria.dto.endereco.RespostaEnderecoDTO;
import br.com.marmitaria.external.viacep.dto.RespostaViaCep;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.marmitaria.dto.endereco.CadastroEnderecoDTO;
import br.com.marmitaria.entity.endereco.Endereco;
import br.com.marmitaria.entity.usuario.Usuario;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EnderecoServiceImpl implements EnderecoService {

    public final EnderecoContext contexto;

	@Override
	@Transactional
	public RespostaEnderecoDTO cadastrarEndereco(CadastroEnderecoDTO dto) {
        Long usuarioId = contexto.authenticatedUser.getId();
		Usuario usuario = contexto.usuarioValidator.validar(usuarioId);

        contexto.enderecoValidator.validarQuantidadeMaxima(usuario.getEnderecos());
        contexto.enderecoValidator.validarDuplicidade(usuario.getId(), dto);

        RespostaViaCep cepInfo = contexto.viaCepService.buscarCep(dto.cep());
		Endereco endereco = contexto.enderecoFactory.criarEndereco(dto, usuario, cepInfo);
		
		contexto.enderecoRepository.save(endereco);
        return contexto.enderecoMapper.paraDTO(endereco);
	}

    @Override
    public List<RespostaEnderecoDTO> listarEnderecosDoUsuario() {
        Long usuarioId = contexto.authenticatedUser.getId();
        Usuario usuario = contexto.usuarioValidator.validar(usuarioId);
        return contexto.enderecoMapper.paraListaDTO(usuario.getEnderecos());
    };

    @Override
    public RespostaEnderecoDTO listarEnderecoPorID(Long id) {
        Long usuarioId = contexto.authenticatedUser.getId();
        Endereco endereco = contexto.enderecoValidator.validar(id, usuarioId);
        return contexto.enderecoMapper.paraDTO(endereco);
    }

    @Override
    @Transactional
    public void removerEnderecoDoUsuario(Long id) {
        Long usuarioId = contexto.authenticatedUser.getId();
        Endereco endereco = contexto.enderecoValidator.validar(id, usuarioId);
        contexto.enderecoRepository.delete(endereco);
    }
}
