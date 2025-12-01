package br.com.marmitaria.service.usuario;

import java.util.List;

import br.com.marmitaria.config.security.AuthenticatedUser;
import br.com.marmitaria.dto.endereco.RespostaEnderecoDTO;
import br.com.marmitaria.dto.usuario.RespostaUsuarioDTO;
import br.com.marmitaria.exception.usuario.UsuarioNaoEncontradoException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.marmitaria.entity.usuario.Usuario;
import br.com.marmitaria.repository.usuario.UsuarioRepository;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioContext contexto;

	@Override
	public List<RespostaUsuarioDTO> listarTodos() {
        List<Usuario> usuarios = contexto.usuarioRepository.findAll();
        return contexto.usuarioMapper.paraListaDTO(usuarios);
	}
	
	@Override
	public RespostaUsuarioDTO buscarUsuario() {
        Long usuarioId = contexto.authenticatedUser.getId();
        Usuario usuario = contexto.usuarioValidator.validar(usuarioId);
        return contexto.usuarioMapper.paraDTO(usuario);
	}

    @Override
    public RespostaUsuarioDTO buscarUsuarioPorID(Long id) {
        Usuario usuario = contexto.usuarioValidator.validar(id);
        return contexto.usuarioMapper.paraDTO(usuario);
    }

    @Override
    @Transactional
    public void removerUsuario(Long id) {
        Usuario usuario = contexto.usuarioValidator.validar(id);
        contexto.usuarioRepository.delete(usuario);
    }
}
