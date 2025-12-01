package br.com.marmitaria.service.endereco;

import br.com.marmitaria.config.security.AuthenticatedUser;
import br.com.marmitaria.dto.endereco.RespostaEnderecoDTO;
import br.com.marmitaria.exception.endereco.EnderecoJaCadastradoException;
import br.com.marmitaria.exception.endereco.EnderecoLimiteCadastradoException;
import br.com.marmitaria.exception.endereco.EnderecoNaoEncontradoException;
import br.com.marmitaria.exception.usuario.UsuarioNaoEncontradoException;
import br.com.marmitaria.external.viacep.dto.ViaCepResponse;
import br.com.marmitaria.external.viacep.service.ViaCepService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.marmitaria.dto.endereco.CadastroEnderecoDTO;
import br.com.marmitaria.entity.endereco.Endereco;
import br.com.marmitaria.entity.usuario.Usuario;
import br.com.marmitaria.repository.endereco.EnderecoRepository;
import br.com.marmitaria.repository.usuario.UsuarioRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EnderecoServiceImpl implements EnderecoService {

    public final EnderecoContext contexto;

	@Override
	@Transactional
	public RespostaEnderecoDTO cadastrarEndereco(CadastroEnderecoDTO dto) {

        Long usuarioId = contexto.authenticatedUser.getId();
		Usuario usuario = contexto.usuarioRepository.findById(usuarioId)
				.orElseThrow(() -> new UsuarioNaoEncontradoException());

        if (usuario.getEnderecos().size() >= 3) {
            throw new EnderecoLimiteCadastradoException();
        }

        if (contexto.enderecoRepository.existsByUsuarioIdAndNumeroAndComplementoIgnoreCase(usuarioId, dto.numero(), dto.complemento())) {
            throw new EnderecoJaCadastradoException();
        }


        ViaCepResponse cepInfo = contexto.viaCepService.buscarCep(dto.cep());
		
		Endereco endereco = new Endereco(
				cepInfo.logradouro(),
                dto.numero(),
                cepInfo.bairro(),
                cepInfo.cidade(),
                cepInfo.estado(),
                dto.complemento(),
                dto.cep(),
                usuario
				);
		
		contexto.enderecoRepository.save(endereco);

        return new RespostaEnderecoDTO(
                endereco.getId(),
                endereco.getLogradouro(),
                endereco.getNumero(),
                endereco.getBairro(),
                endereco.getCidade(),
                endereco.getEstado(),
                endereco.getComplemento(),
                endereco.getCep()
        );
	}

    @Override
    public List<RespostaEnderecoDTO> listarEnderecosDoUsuario() {

        Long usuarioId = contexto.authenticatedUser.getId();
        Usuario usuario = contexto.usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new UsuarioNaoEncontradoException());

        return usuario.getEnderecos().stream()
                .map(e -> new RespostaEnderecoDTO(
                        e.getId(),
                        e.getLogradouro(),
                        e.getNumero(),
                        e.getBairro(),
                        e.getCidade(),
                        e.getEstado(),
                        e.getComplemento(),
                        e.getCep()
                )).toList();
    };

    @Override
    public RespostaEnderecoDTO listarEnderecoDoUsuarioPorID(Long id) {

        Long usuarioId = contexto.authenticatedUser.getId();
        Usuario usuario = contexto.usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new UsuarioNaoEncontradoException());

        Endereco endereco = contexto.enderecoRepository.findById(id)
                .orElseThrow(() -> new EnderecoNaoEncontradoException(id));

        return new RespostaEnderecoDTO(
                endereco.getId(),
                endereco.getLogradouro(),
                endereco.getNumero(),
                endereco.getBairro(),
                endereco.getCidade(),
                endereco.getEstado(),
                endereco.getComplemento(),
                endereco.getCep()
        );
    }

    @Override
    @Transactional
    public void removerEnderecoDoUsuario(Long id) {

        Long usuarioId = contexto.authenticatedUser.getId();
        Usuario usuario = contexto.usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new UsuarioNaoEncontradoException());

        Endereco endereco = contexto.enderecoRepository.findById(id)
                .orElseThrow(() -> new EnderecoNaoEncontradoException(id));

        contexto.enderecoRepository.delete(endereco);
    }
}
