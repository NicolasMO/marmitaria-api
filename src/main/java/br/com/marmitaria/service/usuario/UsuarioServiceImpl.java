package br.com.marmitaria.service.usuario;

import java.util.List;
import java.util.Optional;

import br.com.marmitaria.config.security.AuthenticatedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.marmitaria.entity.usuario.Usuario;
import br.com.marmitaria.repository.usuario.UsuarioRepository;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
	UsuarioRepository usuarioRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticatedUser authenticatedUser;

	@Override
	public List<Usuario> listarTodos() {
		return usuarioRepository.findAll();
	}
	
	@Override
	public Optional<Usuario> buscarUsuario() {
        Long usuarioId = authenticatedUser.getId();
        return usuarioRepository.findById(usuarioId);
	}

    @Override
    @Transactional
    public void removerUsuario(long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        usuarioRepository.delete(usuario);
    }
}
