package br.com.marmitaria.service.auth;

import br.com.marmitaria.dto.auth.LoginDTO;
import br.com.marmitaria.dto.auth.TokenDTO;
import br.com.marmitaria.dto.usuario.CadastroUsuarioDTO;
import br.com.marmitaria.entity.usuario.Usuario;

public interface AuthService {
    TokenDTO login(LoginDTO dto);
    Usuario cadastrarUsuario(CadastroUsuarioDTO cadastroUsuarioDTO);
    String confirmarCadastro(String token);
}