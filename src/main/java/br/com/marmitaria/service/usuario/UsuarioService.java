package br.com.marmitaria.service.usuario;

import java.util.List;
import java.util.Optional;

import br.com.marmitaria.dto.usuario.CadastroUsuarioDTO;
import br.com.marmitaria.entity.usuario.Usuario;

public interface UsuarioService {
	List<Usuario> listarTodos();
	Optional<Usuario> buscarUsuario(Long id);
	Usuario cadastrarUsuario(CadastroUsuarioDTO cadastroUsuarioDTO);
    void removerUsuario(long id);
}
