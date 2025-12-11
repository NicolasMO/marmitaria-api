package br.com.marmitaria.service.usuario;

import java.util.List;

import br.com.marmitaria.dto.usuario.RespostaUsuarioDTO;

public interface UsuarioService {
    List<RespostaUsuarioDTO> listarTodos();
    RespostaUsuarioDTO buscarUsuarioAutenticado();
    RespostaUsuarioDTO buscarUsuarioPorID(Long id);
    void removerUsuario(Long id);
}
