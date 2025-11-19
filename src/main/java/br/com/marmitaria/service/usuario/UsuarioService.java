package br.com.marmitaria.service.usuario;

import java.util.List;
import java.util.Optional;

import br.com.marmitaria.dto.security.LoginRequestDTO;
import br.com.marmitaria.dto.security.LoginResponseDTO;
import br.com.marmitaria.dto.usuario.CadastrarUsuarioDTO;
import br.com.marmitaria.entity.usuario.Usuario;

public interface UsuarioService {
	List<Usuario> buscarTodos();
	Optional<Usuario> buscarUsuario(Long id);
	Usuario cadastrarUsuario(CadastrarUsuarioDTO cadastrarUsuarioDTO);
    void removerUsuario(long id);
}
