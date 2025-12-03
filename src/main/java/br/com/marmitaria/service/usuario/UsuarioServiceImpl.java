package br.com.marmitaria.service.usuario;

import java.util.List;

import br.com.marmitaria.dto.usuario.RespostaUsuarioDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.marmitaria.entity.usuario.Usuario;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioContext contexto;

	@Override
	public List<RespostaUsuarioDTO> listarTodos() {
        List<Usuario> usuarios = contexto.getUsuarioRepository().findAll();
        return contexto.getUsuarioMapper().paraListaDTO(usuarios);
	}
	
	@Override
	public RespostaUsuarioDTO buscarUsuario() {
        Long usuarioId = contexto.getAuthenticatedUser().getId();
        Usuario usuario = contexto.getUsuarioValidator().validar(usuarioId);
        return contexto.getUsuarioMapper().paraDTO(usuario);
	}

    @Override
    public RespostaUsuarioDTO buscarUsuarioPorID(Long id) {
        Usuario usuario = contexto.getUsuarioValidator().validar(id);
        return contexto.getUsuarioMapper().paraDTO(usuario);
    }

    @Override
    @Transactional
    public void removerUsuario(Long id) {
        Usuario usuario = contexto.getUsuarioValidator().validar(id);
        contexto.getUsuarioRepository().delete(usuario);
    }
}
