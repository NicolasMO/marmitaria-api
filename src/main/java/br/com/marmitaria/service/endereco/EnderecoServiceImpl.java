package br.com.marmitaria.service.endereco;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.marmitaria.dto.endereco.CadastrarEnderecoDTO;
import br.com.marmitaria.entity.endereco.Endereco;
import br.com.marmitaria.entity.usuario.Usuario;
import br.com.marmitaria.repository.endereco.EnderecoRepository;
import br.com.marmitaria.repository.usuario.UsuarioRepository;

@Service
public class EnderecoServiceImpl implements EnderecoService {

	private final EnderecoRepository enderecoRepository;
	private final UsuarioRepository usuarioRepository;
	
	public EnderecoServiceImpl(EnderecoRepository enderecoRepository, UsuarioRepository usuarioRepository) {
		this.enderecoRepository = enderecoRepository;
		this.usuarioRepository = usuarioRepository;
	}
	
	@Override
	@Transactional
	public Endereco cadastrarEndereco(CadastrarEnderecoDTO enderecoDTO) {
		Usuario usuario = usuarioRepository.findById(enderecoDTO.getUsuarioId())
				.orElseThrow(() -> new RuntimeException("Usuário não encontrado."));
		
		Endereco endereco = new Endereco(
				enderecoDTO.getLogradouro(),
				enderecoDTO.getNumero(),
				enderecoDTO.getBairro(),
				enderecoDTO.getCidade(),
				enderecoDTO.getEstado(),
				enderecoDTO.getComplemento(),
				enderecoDTO.getCep(),
				usuario
				);
		
		return enderecoRepository.save(endereco);
	}
}
