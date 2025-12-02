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
        Long usuarioId = contexto.getAuthenticatedUser().getId();
		Usuario usuario = contexto.getUsuarioValidator().validar(usuarioId);

        contexto.getEnderecoValidator().validarQuantidadeMaxima(usuario.getEnderecos());
        contexto.getEnderecoValidator().validarDuplicidade(usuario.getId(), dto);

        RespostaViaCep cepInfo = contexto.getViaCepService().buscarCep(dto.cep());
		Endereco endereco = contexto.getEnderecoFactory().criarEndereco(dto, usuario, cepInfo);
		
		contexto.getEnderecoRepository().save(endereco);
        return contexto.getEnderecoMapper().paraDTO(endereco);
	}

    @Override
    public List<RespostaEnderecoDTO> listarEnderecosDoUsuario() {
        Long usuarioId = contexto.getAuthenticatedUser().getId();
        Usuario usuario = contexto.getUsuarioValidator().validar(usuarioId);
        return contexto.getEnderecoMapper().paraListaDTO(usuario.getEnderecos());
    };

    @Override
    public RespostaEnderecoDTO listarEnderecoPorID(Long id) {
        Long usuarioId = contexto.getAuthenticatedUser().getId();
        Endereco endereco = contexto.getEnderecoValidator().validar(id, usuarioId);
        return contexto.getEnderecoMapper().paraDTO(endereco);
    }

    @Override
    @Transactional
    public void removerEnderecoDoUsuario(Long id) {
        Long usuarioId = contexto.getAuthenticatedUser().getId();
        Endereco endereco = contexto.getEnderecoValidator().validar(id, usuarioId);
        contexto.getEnderecoRepository().delete(endereco);
    }
}
