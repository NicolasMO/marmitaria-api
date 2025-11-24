package br.com.marmitaria.service.endereco;

import br.com.marmitaria.config.security.AuthenticatedUser;
import br.com.marmitaria.dto.endereco.RespostaEnderecoDTO;
import br.com.marmitaria.external.viacep.dto.ViaCepResponse;
import br.com.marmitaria.external.viacep.service.ViaCepService;
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
public class EnderecoServiceImpl implements EnderecoService {

    @Autowired
    AuthenticatedUser authenticatedUser;

    @Autowired
	EnderecoRepository enderecoRepository;

    @Autowired
	UsuarioRepository usuarioRepository;

    @Autowired
    ViaCepService viaCepService;

	
	@Override
	@Transactional
	public Endereco cadastrarEndereco(CadastroEnderecoDTO dto) {

        Long usuarioId = authenticatedUser.getId();
		Usuario usuario = usuarioRepository.findById(usuarioId)
				.orElseThrow(() -> new RuntimeException("Usuário não encontrado."));

        if (enderecoRepository.existsByUsuarioIdAndNumeroAndComplementoIgnoreCase(usuarioId, dto.numero(), dto.complemento())) {
            throw new RuntimeException("Endereço já cadastrado.");
        }

        if (usuario.getEnderecos().size() >= 3) {
            throw new RuntimeException(("Máximo de 3 endereços cadastrados."));
        }

        ViaCepResponse cepInfo = viaCepService.buscarCep(dto.cep());
		
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
		
		return enderecoRepository.save(endereco);
	}

    @Override
    public List<RespostaEnderecoDTO> listarEnderecosDoUsuario() {

        Long usuarioId = authenticatedUser.getId();
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));

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
    @Transactional
    public void removerEnderecoDoUsuario(Long id) {

        Long usuarioId = authenticatedUser.getId();
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));

        Endereco endereco = enderecoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Endereço não encontrado."));

        enderecoRepository.delete(endereco);
    }
}
