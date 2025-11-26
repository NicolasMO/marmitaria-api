package br.com.marmitaria.service.usuario;

import java.util.List;
import java.util.Optional;

import br.com.marmitaria.dto.usuario.RespostaUsuarioDTO;
import br.com.marmitaria.entity.usuario.Usuario;

public interface UsuarioService {
    List<RespostaUsuarioDTO> listarTodos();
    RespostaUsuarioDTO buscarUsuario();
    RespostaUsuarioDTO buscarUsuarioPorID(Long id);
    void removerUsuario(long id);
}
