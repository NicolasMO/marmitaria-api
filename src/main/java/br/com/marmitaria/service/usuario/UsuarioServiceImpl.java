package br.com.marmitaria.service.usuario;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.marmitaria.dto.usuario.CadastrarUsuarioDTO;
import br.com.marmitaria.entity.usuario.Usuario;
import br.com.marmitaria.repository.usuario.UsuarioRepository;

@Service
public class UsuarioServiceImpl implements UsuarioService {
	private final UsuarioRepository usuarioRepository;

	public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}
	
	@Override
	public List<Usuario> buscarTodos() {
		return usuarioRepository.findAll();
		
	}
	
	@Override
	public Optional<Usuario> buscarUsuario(Long id) {
		return usuarioRepository.findById(id);
	}

	@Override
	@Transactional
	public Usuario cadastrarUsuario(CadastrarUsuarioDTO cadastrarUsuarioDTO) {
		Usuario usuario = new Usuario(
				cadastrarUsuarioDTO.getNome(), 
				cadastrarUsuarioDTO.getSenha(),
				cadastrarUsuarioDTO.getCelular(),
				cadastrarUsuarioDTO.getEmail()
				);

		System.out.println(usuario);
		return usuarioRepository.save(usuario);
	}
}
